package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class ProtocolProjectDependency extends DelegatingProjectDependency {

    @Inject
    public ProtocolProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":bedrock-codec"
     */
    public BedrockCodecProjectDependency getBedrockCodec() { return new BedrockCodecProjectDependency(getFactory(), create(":bedrock-codec")); }

    /**
     * Creates a project dependency on the project at path ":bedrock-connection"
     */
    public BedrockConnectionProjectDependency getBedrockConnection() { return new BedrockConnectionProjectDependency(getFactory(), create(":bedrock-connection")); }

    /**
     * Creates a project dependency on the project at path ":common"
     */
    public CommonProjectDependency getCommon() { return new CommonProjectDependency(getFactory(), create(":common")); }

}
