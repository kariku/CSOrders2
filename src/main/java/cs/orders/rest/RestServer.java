package cs.orders.rest;

import cs.orders.model.OrderManager;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class RestServer {

    private final OrderManager orderManager = new OrderManager();

    public static void main(String[] args) {
        RestServer me = new RestServer();
        me.run();
    }

    private void run() {
        post("/book/open/:instrument",
                (request, response) -> runService(Services::openBook, request, response));

        post("/book/close/:instrument",
                (request, response) -> runService(Services::closeBook, request, response));

        get("/book/count/:instrument",
                (request, response) -> runService(Services::getBookOrderCount, request, response));

        get("/book/demand/:instrument",
                (request, response) -> runService(Services::getBookDemand, request, response));

        get("/book/biggest/:instrument",
                (request, response) -> runService(Services::getBookBiggestOrder, request, response));

        get("/book/smallest/:instrument",
                (request, response) -> runService(Services::getBookSmallestOrder, request, response));

        get("/book/earliest/:instrument",
                (request, response) -> runService(Services::getBookEarliestOrder, request, response));

        get("/book/last/:instrument",
                (request, response) -> runService(Services::getBookLastOrder, request, response));

        get("/book/limit-breakdown/:instrument",
                (request, response) -> runService(Services::getBookLimitBreakdown, request, response));


        get("/order/valid/:orderid",
                (request, response) -> runService(Services::isOrderValid, request, response));

        get("/order/exec-quantity/:orderid",
                (request, response) -> runService(Services::getOrderExecutionQuantity, request, response));

        get("/order/exec-price/:orderid",
                (request, response) -> runService(Services::getOrderExecutionPrice, request, response));
    }

    private String runService(Service service, Request request, Response response) {
        try {
            return service.run(request, response, orderManager);
        } catch (Exception e) {
            halt(500, e.toString());
            return null;
        }
    }
}
