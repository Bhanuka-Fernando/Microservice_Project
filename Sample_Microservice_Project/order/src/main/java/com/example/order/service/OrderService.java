package com.example.order.service;



import com.example.inventory.dto.InventoryDTO;
import com.example.order.common.ErrorOrderResponse;
import com.example.order.common.SuccessOrderResponse;
import com.example.order.common.orderResponse;
import com.example.order.dto.OrderDTO;
import com.example.order.model.Orders;
import com.example.order.repo.OrderRepo;
import com.example.product.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {
    private final WebClient productWebClient;
    private final WebClient inventoryWebClient;

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient =  inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    // get all orders
    public List<OrderDTO> getAllOrders(){
        List<Orders> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }
    // get a single order
    public OrderDTO getOrderById(Integer orderId){
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
    // save an Order
    public orderResponse saveOrder(OrderDTO orderDTO){

        int itemId = orderDTO.getItemId();

        try{
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/getitem/{itemId}").build(itemId))
                    .retrieve() // to get data or response
                    .bodyToMono(InventoryDTO.class)  // return type
                    .block(); // use the method of bodyToMono

            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/getproducts/{productId}").build(productId))
                    .retrieve() // to get data or response
                    .bodyToMono(ProductDTO.class)  // return type
                    .block(); // use the method of bodyToMono

            assert productResponse != null;

            if(inventoryResponse.getQuantity() > 0 ){
                if(productResponse.getForSale() == 1){
                    orderRepo.save(modelMapper.map(orderDTO, Orders.class));
                }
                else{
                    return new ErrorOrderResponse("Item is not for sale");
                }

                return new SuccessOrderResponse(orderDTO);
            }
            else {
                return new ErrorOrderResponse("Item Not Available");
            }
        }
        catch (WebClientResponseException e){
            if(e.getStatusCode().is5xxServerError()) {
                return new ErrorOrderResponse("Item Not Found");
            }
        }
        return null;

    }
    // update order
    public OrderDTO updateOrder(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO, Orders.class));
        return orderDTO;
    }
    // delete order
    public String deleteOrder(Integer itemId){
        orderRepo.deleteById(itemId);
        return "Order Deleted";
    }


}
