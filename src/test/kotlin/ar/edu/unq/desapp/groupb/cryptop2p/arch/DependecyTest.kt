package ar.edu.unq.desapp.groupb.cryptop2p.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
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
    fun `dto classes should end with DTO or DTOCompanion`() {
        classes().that().haveSimpleNameEndingWith("DTO").or().haveSimpleNameEndingWith("DTO" + "$" + "Companion")
            .should().resideInAPackage("..dto").check(baseClasses)
    }

    @Test
    fun `controller classes should end with controller`() {
        classes().that().resideInAPackage("..webservice")
            .should().haveSimpleNameEndingWith("Controller").check(baseClasses)
    }

    @Test
    fun `controller classes should have spring ControllerAnnotation`() {
        classes().that().resideInAPackage("..webservice")
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses)
    }

    @Test
    fun `controllers should depend on services`() {
        classes().that().resideInAPackage("..webservice")
            .should().dependOnClassesThat()
            .resideInAPackage("..service").check(baseClasses)
    }

    @Test
    fun `the classes of the persistence package should be interfaces`() {
        classes().that().resideInAPackage("..persistence")
            .should().beInterfaces().check(baseClasses)
    }
}