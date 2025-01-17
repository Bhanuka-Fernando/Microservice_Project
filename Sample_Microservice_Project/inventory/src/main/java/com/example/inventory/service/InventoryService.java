package com.example.inventory.service;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.model.Inventory;
import com.example.inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    // get all Inventories
    public List<InventoryDTO> getAllItems(){
        List<Inventory> itemList = inventoryRepo.findAll();
        return modelMapper.map(itemList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    // get an item in inventory
    public InventoryDTO getItemById(Integer itemId){
        Inventory item = inventoryRepo.getItemById(itemId);
        return modelMapper.map(item, InventoryDTO.class);
    }

    // save item in inventory
    public InventoryDTO saveItem(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    // Update item in inventory
    public InventoryDTO updateItem(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    // delete item in inventory
    public String deleteItem(Integer itemId){
        inventoryRepo.deleteById(itemId);
        return "Item Deleted";
    }
}
