package org.jsoar.performancetesting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration
{   
    public class ConfigurationTest
    {
        private final String testName;
        private final String testFile;
        private final String testCategory;
        
        public ConfigurationTest(String testName, String testFile, String testCategory)
        {
            this.testName = testName;
            this.testFile = testFile;
            this.testCategory = testCategory;
        }
        
        public String getTestName()
        {
            return testName;
        }
        
        public String getTestFile()
        {
            return testFile;
        }
        
        public String getTestCategory()
        {
            return testCategory;
        }
    }
    
    public class ConfigurationCategory
    {
        private final String categoryName;
        private final List<String> categoryTests;
        
        public ConfigurationCategory(String categoryName, List<String> categoryTests)
        {
            this.categoryName = categoryName;
            this.categoryTests = categoryTests;
        }
        
        public String getCategoryName()
        {
            return categoryName;
        }
        
        public List<String> getCategoryTests()
        {
            return categoryTests;
        }
        
        public boolean containsTest(String test)
        {
            return categoryTests.contains(test);
        }
        
        public boolean addTest(String test)
        {
            if (containsTest(test))
            {
                return false;
            }
            
            categoryTests.add(test);
            
            return true;
        }
    }
    
    public class UnknownPropertyException extends Exception
    {
        /**
         * 
         */
        private static final long serialVersionUID = 463144412019989054L;
        private final String property;
        
        public UnknownPropertyException(String property)
        {
            super("Unknown Property: " + property);
            
            this.property = property;
        }
        
        public String getProperty()
        {
            return property;
        }
    }
    
    public class InvalidTestNameException extends Exception
    {
        /**
         * 
         */
        private static final long serialVersionUID = -8450373113671237630L;
        private final String property;
        
        public InvalidTestNameException(String property)
        {
            super("Test Property is not a Soar File: " + property);
            
            this.property = property;
        }
        
        public String getProperty()
        {
            return property;
        }
    }
    
    public class MalformedTestCategory extends Exception
    {
        /**
         * 
         */
        private static final long serialVersionUID = -1914521968698486601L;
        private final String property;
        
        public MalformedTestCategory(String property)
        {
            super("Malformed Test Category: " + property);
            
            this.property = property;
        }
        
        public String getProperty()
        {
            return property;
        }
    }
    
    private final String file;
    
    public static final int PARSE_FAILURE = 201;
    public static final int PARSE_SUCCESS = 200;
    
    private final Properties propertiesFile;
    
    private List<ConfigurationCategory> configurationCategories;
    private List<ConfigurationTest> configurationTests;
    
    public Configuration(String file)
    {
        this.file = file;
        
        this.propertiesFile = new Properties();
        
        this.configurationCategories = new ArrayList<ConfigurationCategory>();
        this.configurationTests = new ArrayList<ConfigurationTest>();
    }
    
    public int parse() throws IOException, UnknownPropertyException, InvalidTestNameException, MalformedTestCategory
    {   
        FileInputStream fileStream = new FileInputStream(file);
        propertiesFile.load(fileStream);
        fileStream.close();
        
        configurationCategories.add(new ConfigurationCategory("Uncategorized Tests", new ArrayList<String>()));
                
        for (String key : propertiesFile.stringPropertyNames())
        {
            String value = propertiesFile.getProperty(key);
            
            if (key.startsWith("Category_"))
            {
                //Is a category
                List<String> potentialCategories = Arrays.asList(value.split("\\s+"));
                List<String> actualCategories = new ArrayList<String>();
                
                String temporaryBuffer = "";
                
                for (String potential : potentialCategories)
                {
                    if (potential.startsWith("\""))
                    {
                        if (temporaryBuffer.length() != 0)
                        {
                            throw new MalformedTestCategory(key);
                        }
                        
                        //Use the temporary buffer to calculate the name with spaces
                        temporaryBuffer += potential;
                        continue;
                    }
                    else if (potential.endsWith("\""))
                    {
                        if (temporaryBuffer.length() == 0)
                        {
                            throw new MalformedTestCategory(key);
                        }
                        
                        temporaryBuffer += potential;
                        
                        temporaryBuffer = temporaryBuffer.substring(1, temporaryBuffer.length()-1);
                        
                        actualCategories.add(temporaryBuffer);
                        
                        temporaryBuffer = "";
                        continue;
                    }
                    else if (temporaryBuffer.length() != 0)
                    {
                        temporaryBuffer += potential;
                        continue;
                    }
                    
                    actualCategories.add(potential);
                }
                
                if (temporaryBuffer.length() != 0)
                {
                    throw new MalformedTestCategory(key);
                }
                
                configurationCategories.add(new ConfigurationCategory(key.substring(9), actualCategories));
            }
            else if (key.startsWith("Test_"))
            {
                //Is a test
                String test = key.substring(5);
                
                if (!value.endsWith(".soar"))
                {
                    throw new InvalidTestNameException(test);
                }
                
                String category = "Uncategorized Tests";
                
                for (ConfigurationCategory cc : configurationCategories)
                {
                    if (cc.containsTest(test))
                    {
                        category = cc.getCategoryName();
                        break;
                    }
                }
                
                configurationTests.add(new ConfigurationTest(test, value, category));
            }
            else
            {
                //Unknown
                throw new UnknownPropertyException(key);
            }
        }
        
        return PARSE_SUCCESS;
    }
    
    public List<ConfigurationTest> getConfigurationTests()
    {
        return configurationTests;
    }
    
    public List<ConfigurationCategory> getConfigurationCategories()
    {
        return configurationCategories;
    }
}