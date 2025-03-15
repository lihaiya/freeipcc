/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sxx.jcc.core.view;

/**
 * <p>
 * The export types for the table.
 * </p>
 * 
 * @since 2.0
 * @author Jeff Johnston
 */
public enum ExportType {
    CSV, EXCEL, JEXCEL, PDF, PDFP;
    
    public String toParam() {
        switch (this) {
        case CSV:
            return "csv";
        case EXCEL:
            return "excel";
        case JEXCEL:
            return "jexcel";
        case PDF:
            return "pdf";
        case PDFP:
            return "pdfp";
        default:
            return "";
        }
    }

    public static ExportType valueOfParam(String param) {
        for (ExportType exportType : ExportType.values()) {
            if (exportType.toParam().equals(param)) {
                return exportType;
            }
        }

        return null;
    }    
}
