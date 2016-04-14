package com.datamelt.junit.action;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DateActionTest.class, StringActionTest.class,  MathActionTest.class })
public class AllTests 
{

}
