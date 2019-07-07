package com.kickstarter.mock.factories

import com.kickstarter.models.ShippingRule
import com.kickstarter.services.apiresponses.ShippingRulesEnvelope

class ShippingRulesEnvelopeFactory private constructor() {

    companion object {

        fun shippingRules(): ShippingRulesEnvelope {
            return ShippingRulesEnvelope.builder()
                    .shippingRules(listOf(
                            ShippingRuleFactory.usShippingRule(),
                            ShippingRuleFactory.germanyShippingRule())
                    )
                    .build()
        }

        fun emptyShippingRules(): ShippingRulesEnvelope {
            return ShippingRulesEnvelope.builder()
                    .shippingRules(listOf())
                    .build()
        }
    }
}
