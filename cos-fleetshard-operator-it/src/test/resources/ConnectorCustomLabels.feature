Feature: Metrics With Custom Tags

  Background:
    Given Await configuration
      | atMost       | 30000   |
      | pollDelay    | 100     |
      | pollInterval | 500     |

  Scenario: custom tags
    Given a Connector with:
      | connector.type.id           | log_sink_0.1                    |
      | desired.state               | ready                           |
      | kafka.bootstrap             | kafka.acme.com:443              |
      | operator.id                 | cos-fleetshard-operator-it      |
      | operator.type               | connector-operator-it           |
      | operator.version            | [1.0.0,2.0.0)                   |

    When set connector label "my.cos.bf2.org/version" to "1"
    When set connector annotation "connector.mgmt.bf2.org/organization" to "foo"
    When set connector annotation "connector.mgmt.bf2.org/connector.tier" to "10"

    When deploy
    Then the connector exists
     And the connector secret exists
     And the connector is in phase "Monitor"
     And the deployment is in phase "ready"

     And the meters has entries with name matching "cos.fleetshard.controller.connectors.reconcile\..*" and tags:
       | foo                                   | bar |
       | my.cos.bf2.org.version                | 1   |
       | connector.mgmt.bf2.org.organization   | foo |
       | connector.mgmt.bf2.org.connector.tier | 10  |

    And the meters has entries with name matching "cos.fleetshard.controller.event.operators.operand\..*" and tags:
      | foo                                   | bar |
      | my.cos.bf2.org.version                | 1   |
      | connector.mgmt.bf2.org.organization   | foo |
      | connector.mgmt.bf2.org.connector.tier | 10  |