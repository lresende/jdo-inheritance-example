/* 
 * Copyright 2013 Luciano Resende
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package jdo.example;

import java.util.List;
import java.util.UUID;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import model.Configuration;
import model.DeploymentRule;
import model.PersistenceRule;

import org.junit.Before;
import org.junit.Test;

public class PopulateDBTest {
    private PersistenceManagerFactory persistenceManagerFactory = null;

    @Before
    public void setup() {
        persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }

    @Test
    public void inserted_data_should_be_read_back() throws Exception {
        Configuration configuration = null;

        configuration = createConfigurationWithPersistenceRule();
        persistConfiguration(configuration);

        configuration = createConfigurationDeploymentRule();
        persistConfiguration(configuration);

        List<Configuration> configurations = null;

        configurations = readConfigurationsWithGetExtent();

        for (Configuration c : configurations) {
            System.out.println(c);
        }

    }

    private void persistConfiguration(Configuration configuration) throws Exception {
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
        try {
            persistenceManager.currentTransaction().begin();
            persistenceManager.makePersistent(configuration);
            persistenceManager.currentTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error saving configuration : " + e.getMessage());
            e.printStackTrace();

            throw e;
        } finally {
            if (persistenceManager.currentTransaction().isActive()) {
                persistenceManager.currentTransaction().rollback();
            }
            persistenceManager.close();
        }
    }

    private List<Configuration> readConfigurationsWithGetExtent() throws Exception {
        List<Configuration> configurations = null;
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();

        try {
            persistenceManager.currentTransaction().begin();
            Extent e = persistenceManager.getExtent(Configuration.class, true);
            Query q = persistenceManager.newQuery(e);
            configurations = (List<Configuration>) q.execute();
            persistenceManager.currentTransaction().commit();

            configurations = (List<Configuration>) persistenceManager.newQuery(Configuration.class).execute();
        } catch (Exception e) {
            System.out.println("Error loading configurations : " + e.getMessage());
            e.printStackTrace();

            throw e;
        } finally {
            persistenceManager.close();
        }

        return configurations;
    }

    private static Configuration createConfigurationWithPersistenceRule() {
        PersistenceRule rule = new PersistenceRule();
        rule.setDbName("database");

        Configuration configuration = new Configuration();
        configuration.setId(UUID.randomUUID().toString());
        configuration.getRules().add(rule);

        return configuration;
    }

    private static Configuration createConfigurationDeploymentRule() {
        DeploymentRule rule = new DeploymentRule();
        rule.setNodeName("node");
        Configuration configuration = new Configuration();
        configuration.setId(UUID.randomUUID().toString());
        configuration.getRules().add(rule);

        return configuration;
    }

}
