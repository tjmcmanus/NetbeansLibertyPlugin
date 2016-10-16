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

package org.netbeans.modules.liberty.main;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.DConfigBeanVersionType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DConfigBeanVersionUnsupportedException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.ProgressObject;

public class LibertyDeploymentManager implements DeploymentManager {

    @Override
    public ProgressObject distribute(Target[] target, File file, File file2) throws IllegalStateException {
        return null;
    }

    @Override
    public DeploymentConfiguration createConfiguration(DeployableObject deployableObject) throws InvalidModuleException {
        return null;
    }

    @Override
    public ProgressObject redeploy(TargetModuleID[] targetModuleID, InputStream inputStream, InputStream inputStream2) throws UnsupportedOperationException, IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject distribute(Target[] target, InputStream inputStream, InputStream inputStream2) throws IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject distribute(Target[] target, ModuleType type, InputStream inputStream, InputStream inputStream2) throws IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject undeploy(TargetModuleID[] targetModuleID) throws IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject stop(TargetModuleID[] targetModuleID) throws IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject start(TargetModuleID[] targetModuleID) throws IllegalStateException {
        return null;
    }

    @Override
    public Target[] getTargets() throws IllegalStateException {
        return null;
    }

    @Override
    public void release() {
    }

    @Override
    public boolean isRedeploySupported() {
        return false;
    }

    @Override
    public TargetModuleID[] getAvailableModules(ModuleType moduleType, Target[] target) throws TargetException, IllegalStateException {
        return null;
    }

    @Override
    public TargetModuleID[] getNonRunningModules(ModuleType moduleType, Target[] target) throws TargetException, IllegalStateException {
        return null;
    }

    @Override
    public TargetModuleID[] getRunningModules(ModuleType moduleType, Target[] target) throws TargetException, IllegalStateException {
        return null;
    }

    @Override
    public ProgressObject redeploy(TargetModuleID[] targetModuleID, File file, File file2) throws UnsupportedOperationException, IllegalStateException {
        return null;
    }

    @Override
    public void setLocale(Locale locale) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public boolean isLocaleSupported(Locale locale) {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public void setDConfigBeanVersion(DConfigBeanVersionType dConfigBeanVersionType) throws DConfigBeanVersionUnsupportedException {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public boolean isDConfigBeanVersionSupported(DConfigBeanVersionType dConfigBeanVersionType) {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public Locale getCurrentLocale() {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public DConfigBeanVersionType getDConfigBeanVersion() {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public Locale getDefaultLocale() {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

    @Override
    public Locale[] getSupportedLocales() {
        throw new UnsupportedOperationException("This method should never be called! No need to implement this."); // NOI18N
    }

}
