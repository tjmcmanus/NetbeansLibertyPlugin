/*
 * Copyright 2016 Netbeans Liberty Plugin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.liberty.main.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.netbeans.modules.liberty.main.Trace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class ServerConfigurationFile {
  protected URI uri;
  protected long lastModified = -1;
  protected Document document;
  protected Element serverElement;
  protected Path userDir;
  
  protected String[] TARGETED_APP_TYPE = new String[] { Constants.EAR_APPLICATION, Constants.WEB_APPLICATION, Constants.EJB_APPLICATION };
  protected String[] GENERIC_APP_TYPE = new String[] { "ear", "web", "ejb" };
  
  public ServerConfigurationFile(URI curUri, Path curUserDir) throws IOException {
    uri = curUri;
    userDir = curUserDir;
    load();
  }
  
  private Element getApplicationElement(String name) {
    if (name == null || getServerElement() == null)
        return null;
    
	  Node node = getServerElement().getFirstChild();
	  while (node != null) {
	      if (node.getNodeType() == Node.ELEMENT_NODE) {
	      	String curNodeName = node.getNodeName(); 
	      	if (Constants.APPLICATION.equals(curNodeName) && name.equals(getAppName((Element)node))) {
	          return (Element)node;
	      	} else {
	      		for (String curType : TARGETED_APP_TYPE) {
	  	      	if (curType.equals(curNodeName)) {
	  	      		if (name.equals(getAppName((Element)node))) {
	  	      			return (Element)node;
	  	      		} else {
	  	      			break;
	  	      		}
	  	      	}	      			
	      		}
	      	}
	      }
	      node = node.getNextSibling();
	  }
	  return null;
	}
  
  private String getAppName(Element appElem) {
  	String matchedType = getAppType(appElem);
  	
  	if (matchedType == null) {
  		// This is not an application element
  		return null;
  	}
  	String appName = appElem.getAttribute(Constants.APP_NAME);
  	if (appName!= null) {
  		return appName;
  	}
    return appElem.getAttribute(Constants.INSTANCE_ID);
  }
  
  private void addAppAtributes(Element appElem, Map<String, String> attributes) {
    if (attributes != null && !attributes.isEmpty()) {
        Set<Map.Entry<String, String>> set = attributes.entrySet();
        Iterator<Map.Entry<String, String>> itr = set.iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            appElem.setAttribute(entry.getKey(), entry.getValue());
        }
    }
  }
  
  private String getAppType(Element appElem) {
  	// Find if it is one of the supported application ID
  	String appType = appElem.getNodeName();
  	for (String curType : TARGETED_APP_TYPE) {
  		if (curType.equals(appType)) {
  			return curType;
  		}
  	}
  	
  	// Check for old style generic application element
  	if (Constants.APPLICATION.equals(appType)) {
  		String curAppType = appElem.getAttribute(Constants.APP_TYPE);
    	for (String curType : GENERIC_APP_TYPE) {
    		if (curType.equals(curAppType)) {
    			return curType;
    		}
    	}
  	}
    return null;
  }
  
  public Document getDocument() {
    return document;
  }
  
  /**
   * Add the given application to the configuration, or update an existing application if the
   * name already exists.
   * 
   * @param name
   * @param applicationElement
   * @param location
   * @return true if application has been added; otherwise, return false.
   */
  public boolean addApplication(String name, String applicationElement, String location, Map<String, String> attributes) {
      if (Trace.ENABLED)
          Trace.trace(Trace.INFO, "ConfigurationFile adding application: " + name);
      if (name == null)
          return false;

			for (Element appElem = getFirstChildElement(getServerElement(), applicationElement); appElem != null; appElem = getNextElement(appElem, applicationElement)) {
			    if (name.equals(getAppName(appElem))) {
			        if (location != null && location.equals(appElem.getAttribute(Constants.APP_LOCATION))) {
			        	// application element already exists with same location.
			          return false;
			        }
			        // update the other attributes
			        appElem.setAttribute(Constants.APP_LOCATION, location);
			        addAppAtributes(appElem, attributes);
			        return true;
			    }
			}

      Element appElement = addElement(applicationElement);
      appElement.setAttribute(Constants.INSTANCE_ID, name);
      appElement.setAttribute(Constants.APP_NAME, name);
      appElement.setAttribute(Constants.APP_LOCATION, location);
      addAppAtributes(appElement, attributes);
      return true;
  }
  
  private Element getNextElement(Element element, String name) {
    if (element == null) {
        return null;
    }
    Node node = element.getNextSibling();
    while (node != null) {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(name)) {
            break;
        }
        node = node.getNextSibling();
    }
    return (Element) node;
  }
  
  private Element addElement(String name) {
    return addElement(getServerElement(), name);
  }
  
  private Element addElement(Element parent, String name) {
    if (parent == null)
        return null;

    final Document doc = getDocument();
    boolean isTopLevel = isServerElement(parent);
    NodeList childNodes = parent.getChildNodes();
    boolean hasChildren = !(childNodes == null || childNodes.getLength() == 0
                    || (childNodes.getLength() == 1 && (childNodes.item(0)).getNodeType() == Node.TEXT_NODE && ((childNodes.item(0)).getNodeValue().trim().length() == 0)));

    addPreElementText(parent, isTopLevel, hasChildren);
    Element elem = doc.createElement(name);
    parent.appendChild(elem);
    addPostElementText(parent);

    return elem;
  }
  
  private void addPreElementText(Element parent, boolean isTopLevel, boolean hasChildren) {
    if (parent == null)
        return;

    StringBuilder builder = new StringBuilder();
    if (isTopLevel || !hasChildren) {
        builder.append("\n    ");
    }
    Node node = parent.getParentNode();
    while (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
        builder.append("    ");
        node = node.getParentNode();
    }
    final Text text = getDocument().createTextNode(builder.toString());
    parent.appendChild(text);
  }
  
  private void addPostElementText(Element parent) {
    if (parent == null)
        return;

    StringBuilder builder = new StringBuilder();
    builder.append("\n");
    Node node = parent.getParentNode();
    while (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
        builder.append("    ");
        node = node.getParentNode();
    }
    Node text = getDocument().createTextNode(builder.toString());
    parent.appendChild(text);
  }

  private static Document documentLoad(InputStream in) throws IOException, ParserConfigurationException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder parser = factory.newDocumentBuilder();
    parser.setErrorHandler(new ErrorHandler() {
        @Override
        public void warning(SAXParseException e) throws SAXException {
            // The source view will flag this as a warning
            if (Trace.ENABLED)
                Trace.trace(Trace.WARNING, "Warning while reading configuration file.\nReason: " + e.getMessage());
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            // The source view will flag this error, so we can ignore it.
            // Adding it as a warning to tracing
            Trace.logError("Error while reading configuration file.", e);
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            // The source view will flag this error, so we can ignore it.
            // Adding it as a warning to tracing
            if (Trace.ENABLED)
                Trace.logError("Error while reading configuration file.\nReason: " + e.getMessage(), e);
        }
    });
    return parser.parse(new InputSource(in));
  }
  
  private Element getFirstChildElement(Element element, String name) {
    if (element == null)
        return null;
    Node node = element.getFirstChild();
    while (node != null) {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(name)) {
            break;
        }
        node = node.getNextSibling();
    }
    return (Element) node;
  }

  private Element getServerElement() {
    return serverElement;
  }
  
  private static boolean isServerElement(Element element) {
    return Constants.SERVER_ELEMENT.equals(element.getNodeName());
  }

  private void load() throws IOException {
    InputStream in = null;
    long time = System.currentTimeMillis();
    try {
        File file = new File(uri);
        lastModified = file.lastModified();
        in = new BufferedInputStream(new FileInputStream(file));
        document = documentLoad(in);
        serverElement = document.getDocumentElement();
        // The server.xml needs to start with the server
        if (serverElement == null || !isServerElement(serverElement))
            throw new IOException("Could not read config file: " + uri);
    } catch (FileNotFoundException e) {
        if (Trace.ENABLED)
            Trace.trace(Trace.WARNING, "Invalid file: " + uri, e);
        throw e;
    } catch (IllegalArgumentException e) {
        // caused when includeURI is not a valid file
        if (Trace.ENABLED)
            Trace.trace(Trace.WARNING, "Invalid path: " + uri, e);
        throw new IOException("Could not read config file: " + uri, e);
    } catch (IOException e) {
        Trace.logError("Could not load configuration file: " + uri, e);
        throw e;
    } catch (Exception e) {
        Trace.logError("Could not load configuration file: " + uri, e);
        throw new IOException(e);
    } finally {
        try {
            if (in != null)
                in.close();
        } catch (Exception e) {
            // ignore
        }
        if (Trace.ENABLED)
            Trace.trace(Trace.INFO, "Configuration file load=" + time);
    }
  }
  
  public boolean removeApplication(String appName) {
    if (appName == null)
        return false;
    
    Element matchedAppElement = getApplicationElement(appName);
    if (matchedAppElement != null) {
      removeElement(matchedAppElement);
      return true;
    }

    return false;
  }
  
  /**
   * Remove an element. If the element is a child of the server element
   * then we remove the extra spacing.
   * 
   * @param element
   */
  private void removeElement(Element element) {
    Node parentNode = element.getParentNode();

    // remove prior text
    Node previous = element.getPreviousSibling();
    while (previous != null && previous.getNodeType() == Node.TEXT_NODE && previous.getNodeValue().trim().length() == 0) {
        // Save off the node to remove
        Node nodeToRemove = previous;
        // Get the previous sibling before removing the node, once it is
        // removed and is no longer part of the DOM then getPreviousSibling
        // will not work.
        previous = previous.getPreviousSibling();
        parentNode.removeChild(nodeToRemove);
    }

    // remove element
    parentNode.removeChild(element);

    // clean up single child text node
    NodeList childNodes = parentNode.getChildNodes();
    if (childNodes != null && childNodes.getLength() == 1 && childNodes.item(0).getNodeType() == Node.TEXT_NODE &&
        childNodes.item(0).getNodeValue().trim().length() == 0) {
        parentNode.removeChild(childNodes.item(0));
    }
  }
  
  public void save() throws IOException {
    // If document is null, it might be related to xml parsing errors
    // and we do not want to store an empty file.
    // So, just touch the file to indicate that it was changed
    if (document == null) {
        return;
    }

    BufferedOutputStream w = null;
    try {
        w = new BufferedOutputStream(new FileOutputStream(new File(uri)));
        Result result = new StreamResult(w);
        Source source = new DOMSource(document);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            throw (IOException) (new IOException().initCause(e));
        }
        
        if (Trace.ENABLED) {
        	Trace.trace(Trace.INFO, "Server config file saved successfully: " + uri);
        }
    } catch (IOException e) {
        throw e;
    } catch (Exception e) {
        throw new IOException(e.getLocalizedMessage());
    } finally {
        try {
            if (w != null)
                w.close();
        } catch (Exception e) {
            // ignore
        }
    }
  }
}
