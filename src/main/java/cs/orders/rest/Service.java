package cs.orders.rest;

import cs.orders.model.OrderManager;
import spark.Request;
import spark.Response;

public interface Service {
    String run(Request request, Response response, OrderManager orderManager);
}
