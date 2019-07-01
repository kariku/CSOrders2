package cs.orders.rest;

import com.google.gson.Gson;
import cs.orders.model.Order;
import cs.orders.model.OrderBook;
import cs.orders.model.OrderManager;
import spark.Request;
import spark.Response;

import java.util.Map;

public class Services {
    private Services() {}

    private static final Gson gson = new Gson();

    static String openBook(Request request, Response response, OrderManager orderManager) {
        OrderBook orderBook = orderManager.openOrderBook(request.params(":instrument"));
        return gson.toJson(orderBook);
    }

    static String closeBook(Request request, Response response, OrderManager orderManager) {
        OrderBook orderBook = orderManager.closeOrderBook(request.params(":instrument"));
        return gson.toJson(orderBook);
    }

    static String getBookOrderCount(Request request, Response response, OrderManager orderManager) {
        int count = orderManager.getOrderBook(request.params(":instrument")).getOrderCount();
        return gson.toJson(count);
    }

    static String getBookDemand(Request request, Response response, OrderManager orderManager) {
        double demand = orderManager.getOrderBook(request.params(":instrument")).getDemand();
        return gson.toJson(demand);
    }

    static String getBookBiggestOrder(Request request, Response response, OrderManager orderManager) {
        Order order = orderManager.getOrderBook(request.params(":instrument")).getBiggestOrder();
        return gson.toJson(order);
    }

    static String getBookSmallestOrder(Request request, Response response, OrderManager orderManager) {
        Order order = orderManager.getOrderBook(request.params(":instrument")).getSmallestOrder();
        return gson.toJson(order);
    }

    static String getBookEarliestOrder(Request request, Response response, OrderManager orderManager) {
        Order order = orderManager.getOrderBook(request.params(":instrument")).getEarliestOrder();
        return gson.toJson(order);
    }

    static String getBookLastOrder(Request request, Response response, OrderManager orderManager) {
        Order order = orderManager.getOrderBook(request.params(":instrument")).getLastOrder();
        return gson.toJson(order);
    }

    static String getBookLimitBreakdown(Request request, Response response, OrderManager orderManager) {
        Map<Double, Double> limitBreakdown = orderManager.getOrderBook(request.params(":instrument")).getLimitBreakdown();
        return gson.toJson(limitBreakdown);
    }

    static String isOrderValid(Request request, Response response, OrderManager orderManager) {
        boolean valid = orderManager.isOrderValid(request.params(":orderid"));
        return gson.toJson(valid);
    }

    static String getOrderExecutionQuantity(Request request, Response response, OrderManager orderManager) {
        double quantity = orderManager.getExecutionQuantity(request.params(":orderid"));
        return gson.toJson(quantity);
    }

    static String getOrderExecutionPrice(Request request, Response response, OrderManager orderManager) {
        double price = orderManager.getExecutionPrice(request.params(":orderid"));
        return gson.toJson(price);
    }
}
