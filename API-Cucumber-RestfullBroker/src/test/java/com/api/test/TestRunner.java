package com.api.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty:target/cucumber/cucumber.txt",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"json:target/cucumber/cucumber.json",
				"com.api.utils.MyTestListener"
		}
		,features= {"src/test/resources/features"}
		,glue = {"com.api.stepdefinition"}
		,monochrome = true
		,snippets = SnippetType.CAMELCASE
		,tags = "@RestfullBroker_CRUD"
		)
public class TestRunner {

}