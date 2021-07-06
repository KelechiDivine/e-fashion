package com.houseofo.web.service;

import com.houseofo.data.dtos.DressDto;
import com.houseofo.exceptions.SizeException;
import com.houseofo.exceptions.TypeException;
import com.houseofo.exceptions.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DressService {
    List<DressDto> findAllDresses();
    List <DressDto> findDressByDesigner(String designerBrand) throws UserException;
    List<DressDto> findDressByType(String typeName) throws TypeException;
    List<DressDto> findDressBySize(String size) throws SizeException;
}