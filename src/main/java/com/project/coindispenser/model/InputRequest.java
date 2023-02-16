package com.project.coindispenser.model;

import com.project.coindispenser.util.BillValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputRequest {

    @BillValue(enumClass = Bill.class)
    public String billValue;
    
}
