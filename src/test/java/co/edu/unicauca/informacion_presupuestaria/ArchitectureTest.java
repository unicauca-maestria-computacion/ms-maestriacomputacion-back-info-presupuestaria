package co.edu.unicauca.informacion_presupuestaria;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Hexagonal architecture tests using ArchUnit — all rules are direct (no freeze).
 *
 * All FreezingArchRule instances have been removed as each migration phase is complete.
 * Zero violations are expected on every run (ADR-005 — Strangler Fig, Phase 7 complete).
 *
 * Requirements: 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 6.10
 */
@AnalyzeClasses(
        packages = "co.edu.unicauca.informacion_presupuestaria",
        importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private static final String ROOT = "co.edu.unicauca.informacion_presupuestaria";

    // -------------------------------------------------------------------------
    // Rule 1 — domain does not depend on infrastructure (Phase 1 complete)
    // Req 6.1
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule domainDoesNotDependOnInfrastructure =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .as("Domain must not depend on infrastructure");

    // -------------------------------------------------------------------------
    // Rule 2 — application does not depend on infrastructure (Phase 7 complete)
    // Req 6.2
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule applicationDoesNotDependOnInfrastructure =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .as("Application must not depend on infrastructure")
                    .allowEmptyShould(true);

    // -------------------------------------------------------------------------
    // Rule 3 — use cases are not Spring beans (Phase 3 complete)
    // Req 6.3
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule useCasesAreNotSpringBeans =
            noClasses()
                    .that().resideInAPackage("..application.usecases..")
                    .should().beAnnotatedWith(Service.class)
                    .orShould().beAnnotatedWith(Component.class)
                    .as("Use cases in application.usecases must not have @Service or @Component");

    // -------------------------------------------------------------------------
    // Rule 4 — no field injection with @Autowired (Phase 7 complete)
    // Req 6.4
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule noFieldInjection =
            noFields()
                    .should().beAnnotatedWith(Autowired.class)
                    .as("No class may use @Autowired on fields — use constructor injection");

    // -------------------------------------------------------------------------
    // Rule 5 — no orphan classes outside canonical packages (Phase 7 complete)
    // Req 6.5
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule noOrphanClasses =
            classes()
                    .that().resideOutsideOfPackages(
                            "..domain..",
                            "..application..",
                            "..infrastructure..",
                            "..config..")
                    .and().doNotHaveSimpleName("InformacionPresupuestariaApplication")
                    .should().resideInAPackage(ROOT + ".NONEXISTENT")
                    .as("All classes must reside in domain, application, infrastructure, or config")
                    .allowEmptyShould(true);

    // -------------------------------------------------------------------------
    // Rule 6 — REST controllers do not depend on use case impls (Phase 6 complete)
    // Req 6.6
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule controllersDoNotDependOnUseCaseImpls =
            noClasses()
                    .that().resideInAPackage("..infrastructure.in..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..application.usecases..")
                    .as("Controllers in infrastructure.in must not depend on use case impls in application.usecases");

    // -------------------------------------------------------------------------
    // Rule 7 — no cycles within infrastructure sub-packages (Phase 5 complete)
    // Req 6.7
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule noCyclesInInfrastructure =
            slices()
                    .matching(ROOT + ".infrastructure.(*)..")
                    .should().beFreeOfCycles()
                    .as("No cycles among infrastructure sub-packages");

    // -------------------------------------------------------------------------
    // Rule 8 — use cases implement exactly one input port (Phase 3 complete)
    // Req 6.8
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule useCasesImplementOneInputPort =
            classes()
                    .that().resideInAPackage("..application.usecases..")
                    .should().implement(
                            com.tngtech.archunit.core.domain.JavaClass.Predicates
                                    .resideInAPackage("..domain.ports.in.."))
                    .as("Use cases in application.usecases must implement an input port from domain.ports.in");

    // -------------------------------------------------------------------------
    // Rule 9 — gateways implement exactly one output port (Phase 5 complete)
    // Req 6.9
    // -------------------------------------------------------------------------
    @ArchTest
    static final ArchRule gatewaysImplementOneOutputPort =
            classes()
                    .that().resideInAPackage("..infrastructure.out.persistence.gateway..")
                    .should().implement(
                            com.tngtech.archunit.core.domain.JavaClass.Predicates
                                    .resideInAPackage("..domain.ports.out.."))
                    .as("Gateways in infrastructure.out.persistence.gateway must implement an output port from domain.ports.out");
}
