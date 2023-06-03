package ar.edu.unq.desapp.groupb.cryptop2p.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.conditions.ArchConditions.haveSimpleNameEndingWith
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DependecyTest {

    private lateinit var baseClasses: JavaClasses


    @BeforeEach
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)

            .importPackages("ar.edu.unq.desapp.groupb.cryptop2p")
    }

    @Test
    fun dtoClassesShouldEndWithDTOOrDTOCompanion() {
        classes().that().resideInAPackage("..dto")
            .should(haveSimpleNameEndingWith("DTO").or(haveSimpleNameEndingWith("DTO" + "$" + "Companion")))
            .check(baseClasses)
    }

    @Test
    fun controllerClassesShouldEndWithController() {
        classes().that().resideInAPackage("..webservice")
            .should().haveSimpleNameEndingWith("Controller").check(baseClasses)
    }

    @Test
    fun controllerClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..webservice")
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses)
    }

    @Test
    fun serviceClassesShouldEndWithService() {
        classes().that().resideInAPackage("..service")
            .should().haveSimpleNameEndingWith("Service").check(baseClasses)
    }

    @Test
    fun serviceClassesShouldHaveSpringServiceAnnotation() {
        classes().that().resideInAPackage("..service")
            .should().beAnnotatedWith("org.springframework.stereotype.Service")
            .check(baseClasses)
    }


}