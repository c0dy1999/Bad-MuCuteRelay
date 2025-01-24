package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final CheckerLibraryAccessors laccForCheckerLibraryAccessors = new CheckerLibraryAccessors(owner);
    private final FastutilLibraryAccessors laccForFastutilLibraryAccessors = new FastutilLibraryAccessors(owner);
    private final NettyLibraryAccessors laccForNettyLibraryAccessors = new NettyLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>jose4j</b> with <b>org.bitbucket.b_c:jose4j</b> coordinates and
     * with version <b>0.9.6</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJose4j() {
        return create("jose4j");
    }

    /**
     * Dependency provider for <b>lmbda</b> with <b>org.lanternpowered:lmbda</b> coordinates and
     * with version <b>2.0.0</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getLmbda() {
        return create("lmbda");
    }

    /**
     * Dependency provider for <b>math</b> with <b>org.cloudburstmc.math:immutable</b> coordinates and
     * with version <b>2.0-SNAPSHOT</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getMath() {
        return create("math");
    }

    /**
     * Dependency provider for <b>natives</b> with <b>com.nukkitx:natives</b> coordinates and
     * with version <b>1.0.3</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getNatives() {
        return create("natives");
    }

    /**
     * Dependency provider for <b>nbt</b> with <b>org.cloudburstmc:nbt</b> coordinates and
     * with version <b>3.0.3.Final</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getNbt() {
        return create("nbt");
    }

    /**
     * Dependency provider for <b>snappy</b> with <b>io.airlift:aircompressor</b> coordinates and
     * with version <b>0.25</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getSnappy() {
        return create("snappy");
    }

    /**
     * Group of libraries at <b>checker</b>
     */
    public CheckerLibraryAccessors getChecker() {
        return laccForCheckerLibraryAccessors;
    }

    /**
     * Group of libraries at <b>fastutil</b>
     */
    public FastutilLibraryAccessors getFastutil() {
        return laccForFastutilLibraryAccessors;
    }

    /**
     * Group of libraries at <b>netty</b>
     */
    public NettyLibraryAccessors getNetty() {
        return laccForNettyLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class CheckerLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public CheckerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>checker</b> with <b>org.checkerframework:checker</b> coordinates and
         * with version reference <b>checkerframework</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("checker");
        }

        /**
         * Dependency provider for <b>qual</b> with <b>org.checkerframework:checker-qual</b> coordinates and
         * with version reference <b>checkerframework</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getQual() {
            return create("checker.qual");
        }

    }

    public static class FastutilLibraryAccessors extends SubDependencyFactory {
        private final FastutilIntLibraryAccessors laccForFastutilIntLibraryAccessors = new FastutilIntLibraryAccessors(owner);
        private final FastutilLongLibraryAccessors laccForFastutilLongLibraryAccessors = new FastutilLongLibraryAccessors(owner);
        private final FastutilObjectLibraryAccessors laccForFastutilObjectLibraryAccessors = new FastutilObjectLibraryAccessors(owner);

        public FastutilLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bom</b> with <b>org.cloudburstmc.fastutil:bom</b> coordinates and
         * with version <b>8.5.15</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBom() {
            return create("fastutil.bom");
        }

        /**
         * Group of libraries at <b>fastutil.int</b>
         */
        public FastutilIntLibraryAccessors getInt() {
            return laccForFastutilIntLibraryAccessors;
        }

        /**
         * Group of libraries at <b>fastutil.long</b>
         */
        public FastutilLongLibraryAccessors getLong() {
            return laccForFastutilLongLibraryAccessors;
        }

        /**
         * Group of libraries at <b>fastutil.object</b>
         */
        public FastutilObjectLibraryAccessors getObject() {
            return laccForFastutilObjectLibraryAccessors;
        }

    }

    public static class FastutilIntLibraryAccessors extends SubDependencyFactory {
        private final FastutilIntObjectLibraryAccessors laccForFastutilIntObjectLibraryAccessors = new FastutilIntObjectLibraryAccessors(owner);

        public FastutilIntLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>fastutil.int.object</b>
         */
        public FastutilIntObjectLibraryAccessors getObject() {
            return laccForFastutilIntObjectLibraryAccessors;
        }

    }

    public static class FastutilIntObjectLibraryAccessors extends SubDependencyFactory {

        public FastutilIntObjectLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>maps</b> with <b>org.cloudburstmc.fastutil.maps:int-object-maps</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaps() {
            return create("fastutil.int.object.maps");
        }

    }

    public static class FastutilLongLibraryAccessors extends SubDependencyFactory {
        private final FastutilLongObjectLibraryAccessors laccForFastutilLongObjectLibraryAccessors = new FastutilLongObjectLibraryAccessors(owner);

        public FastutilLongLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>common</b> with <b>org.cloudburstmc.fastutil.commons:long-common</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("fastutil.long.common");
        }

        /**
         * Group of libraries at <b>fastutil.long.object</b>
         */
        public FastutilLongObjectLibraryAccessors getObject() {
            return laccForFastutilLongObjectLibraryAccessors;
        }

    }

    public static class FastutilLongObjectLibraryAccessors extends SubDependencyFactory {

        public FastutilLongObjectLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>maps</b> with <b>org.cloudburstmc.fastutil.maps:long-object-maps</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaps() {
            return create("fastutil.long.object.maps");
        }

    }

    public static class FastutilObjectLibraryAccessors extends SubDependencyFactory {
        private final FastutilObjectIntLibraryAccessors laccForFastutilObjectIntLibraryAccessors = new FastutilObjectIntLibraryAccessors(owner);

        public FastutilObjectLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>fastutil.object.int</b>
         */
        public FastutilObjectIntLibraryAccessors getInt() {
            return laccForFastutilObjectIntLibraryAccessors;
        }

    }

    public static class FastutilObjectIntLibraryAccessors extends SubDependencyFactory {

        public FastutilObjectIntLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>maps</b> with <b>org.cloudburstmc.fastutil.maps:object-int-maps</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaps() {
            return create("fastutil.object.int.maps");
        }

    }

    public static class NettyLibraryAccessors extends SubDependencyFactory {
        private final NettyTransportLibraryAccessors laccForNettyTransportLibraryAccessors = new NettyTransportLibraryAccessors(owner);

        public NettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>buffer</b> with <b>io.netty:netty-buffer</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBuffer() {
            return create("netty.buffer");
        }

        /**
         * Group of libraries at <b>netty.transport</b>
         */
        public NettyTransportLibraryAccessors getTransport() {
            return laccForNettyTransportLibraryAccessors;
        }

    }

    public static class NettyTransportLibraryAccessors extends SubDependencyFactory {

        public NettyTransportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>raknet</b> with <b>org.cloudburstmc.netty:netty-transport-raknet</b> coordinates and
         * with version <b>1.0.0.CR3-SNAPSHOT</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRaknet() {
            return create("netty.transport.raknet");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>checkerframework</b> with value <b>3.37.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCheckerframework() { return getVersion("checkerframework"); }

        /**
         * Version alias <b>netty</b> with value <b>4.1.101.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNetty() { return getVersion("netty"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>checkerframework</b> with plugin id <b>org.checkerframework</b> and
         * with version <b>0.6.29</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getCheckerframework() { return createPlugin("checkerframework"); }

        /**
         * Plugin provider for <b>lombok</b> with plugin id <b>io.freefair.lombok</b> and
         * with version <b>6.6.3</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLombok() { return createPlugin("lombok"); }

    }

}
