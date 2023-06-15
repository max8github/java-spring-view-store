package store.order.api;

import kalix.javasdk.valueentity.ValueEntity;
import kalix.javasdk.annotations.EntityKey;
import kalix.javasdk.annotations.EntityType;
import org.springframework.web.bind.annotation.*;
import store.order.domain.Order;

import java.time.Instant;

@EntityType("order")
@EntityKey("id")
@RequestMapping("/order/{id}")
public class OrderEntity extends ValueEntity<Order> {

  @GetMapping()
  public Effect<Order> get() {
    return effects().reply(currentState());
  }

  @PostMapping("/create")
  public Effect<String> create(@RequestBody CreateOrder createOrder) {
    Order order =
        new Order(
            commandContext().entityId(),
            createOrder.productId(),
            createOrder.customerId(),
            createOrder.quantity(),
            Instant.now().toEpochMilli());
    return effects().updateState(order).thenReply("OK");
  }
}
