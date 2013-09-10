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

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class PersistenceRule extends Rule {

    @Persistent
    private String dbName;

    public PersistenceRule() {

    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
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
        PersistenceRule other = (PersistenceRule) obj;
        if (dbName == null) {
            if (other.dbName != null)
                return false;
        } else if (!dbName.equals(other.dbName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersistenceRule [");
        if (dbName != null)
            builder.append("dbName=").append(dbName);
        builder.append("]");
        return builder.toString();
    }

}
