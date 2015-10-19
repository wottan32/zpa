/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015 Felipe Zorzo
 * felipe.b.zorzo@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package br.com.felipezorzo.sonar.plsql.api.units;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.felipezorzo.sonar.plsql.api.PlSqlGrammar;
import br.com.felipezorzo.sonar.plsql.api.RuleTest;

public class CreateProcedureTest extends RuleTest {

    @Before
    public void init() {
        setRootRule(PlSqlGrammar.CREATE_PROCEDURE);
    }

    @Test
    public void matchesSimpleProcedure() {
        assertThat(p).matches(""
                + "create procedure test is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesSimpleProcedureAlternative() {
        assertThat(p).matches(""
                + "create procedure test as\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesSimpleCreateOrReplaceProcedure() {
        assertThat(p).matches(""
                + "create or replace procedure test is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithSchema() {
        assertThat(p).matches(""
                + "create procedure schema.test is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
   
    @Test
    public void matchesProcedureWithParameter() {
        assertThat(p).matches(""
                + "create procedure test(parameter in number) is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithMultipleParameters() {
        assertThat(p).matches(""
                + "create procedure test(parameter1 in number, parameter2 in number) is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithAuthidCurrentUser() {
        assertThat(p).matches(""
                + "create procedure test authid current_user is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithAuthidDefiner() {
        assertThat(p).matches(""
                + "create procedure test authid definer is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithJavaCallSpec() {
        assertThat(p).matches("create procedure test is language java 'javatest';");
    }
    
    @Test
    public void matchesExternalProcedure() {
        assertThat(p).matches("create procedure test is external;");
    }
    
    @Test
    public void matchesProcedureWithVariableDeclaration() {
        assertThat(p).matches(""
                + "create procedure test is\n"
                + "var number;"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithMultipleVariableDeclaration() {
        assertThat(p).matches(""
                + "create procedure test is\n"
                + "var number;"
                + "var2 number;"
                + "begin\n"
                + "null;\n"
                + "end;");
    }
    
    @Test
    public void matchesProcedureWithTimestamp() {
        assertThat(p).matches(""
                + "create procedure test timestamp '2015-01-01' is\n"
                + "begin\n"
                + "null;\n"
                + "end;");
    }

}