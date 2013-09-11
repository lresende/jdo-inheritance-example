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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import model.Configuration;
import model.DeploymentRule;
import model.PersistenceRule;

import org.junit.Before;
import org.junit.Test;

public class ReadDBTest {
    private static PersistenceManagerFactory persistenceManagerFactory = null;

    @Before
    public void setup() {
        persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }

    @Test
    public void inserted_data_should_be_read_back() throws Exception {
        List<Configuration> configurations = null;

        configurations = readConfigurations();

        for (Configuration configuration : configurations) {
            System.out.println(configuration);
        }

    }

    private List<Configuration> readConfigurations() throws Exception {
        List<Configuration> configurations = null;
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();

        try {
            configurations = (List<Configuration>) persistenceManager.newQuery(Configuration.class).execute();
            for (Configuration configuration : configurations) {
                System.out.println(configuration);
            }
        } catch (Exception e) {
            System.out.println("Error loading configurations : " + e.getMessage());
            e.printStackTrace();

            throw e;
        } finally {
            persistenceManager.close();
        }

        return configurations;
    }

    @SuppressWarnings("rawtypes")
    private List<Configuration> readConfigurationsWithGetExtent() throws Exception {
        List<Configuration> configurations = new ArrayList<Configuration>();
        PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();

        try {
            System.out.println(">>> Set max fetch deep");
            persistenceManager.getFetchPlan().setMaxFetchDepth(3);

            System.out.println(">>> Get extent");
            Extent extent = persistenceManager.getExtent(Configuration.class, true);
            System.out.println(">>> Get extent iterator");
            Iterator<Configuration> iterator = extent.iterator();
            while (iterator.hasNext()) {
                System.out.println(">>> Iterate next");
                Configuration configuration = (Configuration) iterator.next();

                System.out.println(configuration);

                // System.out.println(">>> Retrieve all");
                // persistenceManager.retrieveAll(configuration);
                configurations.add(configuration);

                // System.out.println(configuration);
            }
            // Query q = persistenceManager.newQuery(extent);

            // configurations = (List<Configuration>) q.execute();

            System.out.println(">>> Close all");
            // extent.closeAll();

            // configurations = (List<Configuration>) persistenceManager.newQuery(Configuration.class).execute();
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
