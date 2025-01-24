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
    private final JunitLibraryAccessors laccForJunitLibraryAccessors = new JunitLibraryAccessors(owner);
    private final NettyLibraryAccessors laccForNettyLibraryAccessors = new NettyLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>expiringmap</b> with <b>net.jodah:expiringmap</b> coordinates and
     * with version <b>0.5.10</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getExpiringmap() {
        return create("expiringmap");
    }

    /**
     * Group of libraries at <b>junit</b>
     */
    public JunitLibraryAccessors getJunit() {
        return laccForJunitLibraryAccessors;
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

    public static class JunitLibraryAccessors extends SubDependencyFactory {
        private final JunitJupiterLibraryAccessors laccForJunitJupiterLibraryAccessors = new JunitJupiterLibraryAccessors(owner);

        public JunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>junit.jupiter</b>
         */
        public JunitJupiterLibraryAccessors getJupiter() {
            return laccForJunitJupiterLibraryAccessors;
        }

    }

    public static class JunitJupiterLibraryAccessors extends SubDependencyFactory {

        public JunitJupiterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>org.junit.jupiter:junit-jupiter-api</b> coordinates and
         * with version reference <b>junit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("junit.jupiter.api");
        }

        /**
         * Dependency provider for <b>engine</b> with <b>org.junit.jupiter:junit-jupiter-engine</b> coordinates and
         * with version reference <b>junit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEngine() {
            return create("junit.jupiter.engine");
        }

        /**
         * Dependency provider for <b>params</b> with <b>org.junit.jupiter:junit-jupiter-params</b> coordinates and
         * with version reference <b>junit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getParams() {
            return create("junit.jupiter.params");
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
         * Dependency provider for <b>codec</b> with <b>io.netty:netty-codec</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCodec() {
            return create("netty.codec");
        }

        /**
         * Dependency provider for <b>common</b> with <b>io.netty:netty-common</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("netty.common");
        }

        /**
         * Group of libraries at <b>netty.transport</b>
         */
        public NettyTransportLibraryAccessors getTransport() {
            return laccForNettyTransportLibraryAccessors;
        }

    }

    public static class NettyTransportLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final NettyTransportNativeLibraryAccessors laccForNettyTransportNativeLibraryAccessors = new NettyTransportNativeLibraryAccessors(owner);

        public NettyTransportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>transport</b> with <b>io.netty:netty-transport</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("netty.transport");
        }

        /**
         * Group of libraries at <b>netty.transport.native</b>
         */
        public NettyTransportNativeLibraryAccessors getNative() {
            return laccForNettyTransportNativeLibraryAccessors;
        }

    }

    public static class NettyTransportNativeLibraryAccessors extends SubDependencyFactory {
        private final NettyTransportNativeUnixLibraryAccessors laccForNettyTransportNativeUnixLibraryAccessors = new NettyTransportNativeUnixLibraryAccessors(owner);

        public NettyTransportNativeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>netty.transport.native.unix</b>
         */
        public NettyTransportNativeUnixLibraryAccessors getUnix() {
            return laccForNettyTransportNativeUnixLibraryAccessors;
        }

    }

    public static class NettyTransportNativeUnixLibraryAccessors extends SubDependencyFactory {

        public NettyTransportNativeUnixLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>common</b> with <b>io.netty:netty-transport-native-unix-common</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("netty.transport.native.unix.common");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>junit</b> with value <b>5.9.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

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

        /**
         * Dependency bundle provider for <b>junit</b> which contains the following dependencies:
         * <ul>
         *    <li>org.junit.jupiter:junit-jupiter-engine</li>
         *    <li>org.junit.jupiter:junit-jupiter-api</li>
         *    <li>org.junit.jupiter:junit-jupiter-params</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJunit() {
            return createBundle("junit");
        }

        /**
         * Dependency bundle provider for <b>netty</b> which contains the following dependencies:
         * <ul>
         *    <li>io.netty:netty-common</li>
         *    <li>io.netty:netty-buffer</li>
         *    <li>io.netty:netty-codec</li>
         *    <li>io.netty:netty-transport</li>
         *    <li>io.netty:netty-transport-native-unix-common</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getNetty() {
            return createBundle("netty");
        }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
