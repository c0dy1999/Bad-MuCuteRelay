package com.mucheng.mucute.relay.definition

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition
import org.cloudburstmc.protocol.common.SimpleDefinitionRegistry

object Definitions {

    val legacyIdMap: MutableMap<Int, String> = HashMap()

    val itemDataList: MutableList<DataEntry> = ArrayList()

    var itemDefinitions: SimpleDefinitionRegistry<ItemDefinition> = SimpleDefinitionRegistry.builder<ItemDefinition>()
        .build()

    var blockDefinitions: SimpleDefinitionRegistry<BlockDefinition> = SimpleDefinitionRegistry.builder<BlockDefinition>()
        .build()

}