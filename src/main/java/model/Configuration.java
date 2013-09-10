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
package model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(detachable = "true")
@Version(strategy = VersionStrategy.VERSION_NUMBER)
@FetchGroup(name = "fetchgroup", members = { @Persistent(name = "rules") })
public class Configuration {

    @PrimaryKey
    @Column(name = "CONFIGURATION_ID")
    private String id;

    @Persistent(dependentElement = "true")
    @Element(column = "CONFIGURATION_ID")
    // @Column(name = "CONFIGURATION_ID")
    private List<Rule> rules;

    public Configuration() {
        rules = new ArrayList<Rule>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((rules == null) ? 0 : rules.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Configuration other = (Configuration) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (rules == null) {
            if (other.rules != null)
                return false;
        } else if (!rules.equals(other.rules))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Configuration [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (rules != null)
            builder.append("rules[size=").append(rules.size()).append("]").append(rules);
        builder.append("]");
        return builder.toString();
    }

}
