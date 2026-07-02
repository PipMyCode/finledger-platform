package com.finledger;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.finledger")
public class ArchitectureTest {

    @ArchTest
    static final ArchRule domainShouldBePure = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "..application..",
                    "..infrastructure..",
                    "..presentation..",
                    "org.springframework.."
            )
            .because("The Domain layer must be 100% pure Java and independent of all other layers.");

}
