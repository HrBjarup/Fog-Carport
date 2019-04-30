/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.databaseAccessObjects.mappers.MaterialMapper;
import data.databaseAccessObjects.mappers.OrderMapper;
import data.exceptions.LoginException;
import data.models.MaterialModel;
import data.models.OrderModel;
import data.models.PartslistModel;
import logic.LogicFacadeImpl;

/**
 *
 * @author Camilla
 */
public class DataFacadeImpl implements DataFacade
{
    
    private static DataFacadeImpl instance = null;

    public synchronized static DataFacadeImpl getInstance() {
        if (instance == null) {
            instance = new DataFacadeImpl();
        }
        return instance;
    }

    @Override
    public MaterialModel getMaterial(int id) throws LoginException
    {
        return MaterialMapper.getInstance().getMaterial(id);
    }

    @Override
    public OrderModel getOrder(int id) throws LoginException
    {
        return OrderMapper.getInstance().getOrder(id);
    }

    @Override
    public PartslistModel getBOM()
    {
        return new PartslistModel();
    }

    @Override
    public PartslistModel getOrderDetails(int id) throws LoginException
    {
        return MaterialMapper.getInstance().getMaterials(id);
    }

    @Override
    public void createOrder(OrderModel order) throws LoginException
    {
        OrderMapper.getInstance().createOrder(order);
    }

}
