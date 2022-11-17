package de.jjohannes.gradle.javaecosystem.capabilities.rules;

import de.jjohannes.gradle.javaecosystem.capabilities.util.VersionNumber;
import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.CacheableRule;
import org.gradle.api.artifacts.ComponentMetadataContext;
import org.gradle.api.artifacts.ComponentMetadataRule;

@CacheableRule
@NonNullApi
public abstract class JavaxEjbApiRule implements ComponentMetadataRule {

    public static final String CAPABILITY_GROUP = "javax.ejb";
    public static final String CAPABILITY_NAME = "ejb";
    public static final String CAPABILITY = CAPABILITY_GROUP + ":" + CAPABILITY_NAME;

    public static final String FIRST_JAKARTA_VERSION = "4.0.0";

    public static final String[] MODULES = {
            "jakarta.ejb:jakarta.ejb-api",
            "javax.ejb:javax.ejb-api",
            "javax.ejb:ejb-api"
    };

    @Override
    public void execute(ComponentMetadataContext context) {
        String version = context.getDetails().getId().getVersion();

        if (VersionNumber.parse(version).compareTo(VersionNumber.parse(FIRST_JAKARTA_VERSION)) < 0) {
            context.getDetails().allVariants(variant -> variant.withCapabilities(capabilities ->
                    capabilities.addCapability(CAPABILITY_GROUP, CAPABILITY_NAME, version)
            ));
        }
    }
}