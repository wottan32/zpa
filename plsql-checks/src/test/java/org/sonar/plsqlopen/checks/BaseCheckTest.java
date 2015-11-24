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
package org.sonar.plsqlopen.checks;

import java.io.File;
import java.util.Collection;
import java.util.Locale;

import org.junit.Before;
import org.mockito.Mock;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.plsqlopen.AnalyzerMessage;
import org.sonar.plsqlopen.SonarComponents;
import org.sonar.plsqlopen.squid.PlSqlAstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.SourceFile;

import com.sonar.sslr.api.Grammar;

public class BaseCheckTest {

    private final String defaultResourceFolder = "src/test/resources/checks/";
    private DefaultFileSystem fs = new DefaultFileSystem(new File("."));
    
    @Mock
    SensorContext context;
    
    @Mock
    ResourcePerspectives resourcePerspectives;
    
    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }
    
    protected SourceFile scanSingleFile(String filename, SquidAstVisitor<Grammar> check) {
        String relativePath = defaultResourceFolder + filename;
        DefaultInputFile inputFile = new DefaultInputFile(relativePath).setLanguage("plsqlopen");
        inputFile.setAbsolutePath((new File(relativePath)).getAbsolutePath());
        fs.add(inputFile);
        
        SonarComponents components = new SonarComponents(resourcePerspectives, context, fs).getTestInstance();
        
        return PlSqlAstScanner.scanSingleFile(new File(defaultResourceFolder + filename), components, check);
    }
    
    protected Collection<AnalyzerMessage> scanFile(String filename, SquidAstVisitor<Grammar> check) {
        String relativePath = defaultResourceFolder + filename;
        DefaultInputFile inputFile = new DefaultInputFile(relativePath).setLanguage("plsqlopen");
        inputFile.setAbsolutePath((new File(relativePath)).getAbsolutePath());
        fs.add(inputFile);
        
        SonarComponents components = new SonarComponents(resourcePerspectives, context, fs).getTestInstance();
        
        PlSqlAstScanner.scanSingleFile(new File(defaultResourceFolder + filename), components, check);
        return ((SonarComponents.Test) components).getIssues();
    }
    
}