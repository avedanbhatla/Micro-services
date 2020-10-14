package com.lzb.camel.camel;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import java.util.stream.Collectors;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.dataformat.JsonLibrary;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.lzb.hr.beans.objectbeanlist.EmployeeBenefitsList;
import com.lzb.hr.beans.objectbeanlist.Employees;
import com.lzb.hr.beans.objectbeans.Employee;
import com.lzb.hr.beans.objectbeans.EmployeeBenefits;
import com.lzb.hr.beans.vendorbeanlist.EquifaxList;
import com.lzb.hr.beans.vendorbeans.Equifax;

@Component(value = "")
class EmployeeRouteBuilder extends RouteBuilder
{

/*
    @Value("${route.from.path}")
    String fromPath;
    
    @Value("${route.to.path}")
    String toPath;*/
    
    @Value("${route.from.ws1}")
    String ws1Url;
    
    @Value("${route.from.ws2}")
    String ws2Url;

	 
    
    @Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
    	
    	
    	restConfiguration();
    	
    	rest("/employeenew")
        .get("/").description("").outType(EquifaxList.class)
        .route()
        .setHeader("Content-Type", constant("application/json"))
        .setHeader("Accept", constant("application/json"))
        .to(ws1Url)
        .unmarshal()
        .json(JsonLibrary.Jackson,Employees.class)
        .enrich("direct:Web-service2", new AggregationStrategy() {
			
			@Override
			public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
				// TODO combines to messages and business exception logic (if any)
				
				
				// convert the message to bean
				Employees emp = oldExchange.getIn().getBody(Employees.class);
				EmployeeBenefitsList empB = newExchange.getIn().getBody(EmployeeBenefitsList.class);
				
				HashMap<Integer,Equifax> map = new HashMap<Integer,Equifax>();
				List<Equifax> list = new ArrayList<Equifax>();
				
				
				for(Employee e: emp.getEmployeesList()){
					
					for(EmployeeBenefits b: empB.getEmployeesList()){
					
						
						map.put(e.getId(), new com.lzb.hr.beans.vendorbeans.Equifax(Integer.toString(e.getId()), e.getFirstName(), e.getLastName(), b.getDentalBen(), b.getMedicalBen()));
					
					}
					
				} 
				
			    //map to list 
				list = map.values().stream().collect(Collectors.toList());
		
				newExchange.getMessage().setBody(new EquifaxList(list));
				
				return newExchange;
			}
		})
        .log("before marshal to pojo: ${body}")
        .marshal()
        .json(JsonLibrary.Jackson,com.lzb.hr.beans.vendorbeanlist.EquifaxList.class)
        .log("Final data: ${body}")
        .to("log:result")
        .endRest();

		
    	
    	from("direct:Web-service2")
		        .transform().simple("${null}")
		        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
		        .removeHeaders("CamelHttp*")
		        .to(ws2Url)
		        .unmarshal()
		        .json(JsonLibrary.Jackson,EmployeeBenefitsList.class)
		.to("log:result");
    	
   
		}
    
    
    /*
	 * Without Bindy. String level manipulation
	 * 
	*/
	/*
	StringBuilder csv = new StringBuilder();
	for(int i = 0 ; i < emp.getEmployeesList().size(); i++)
	{
	csv.append(emp.getEmployeesList().get(i).getFirstName());
	csv.append(",");
	csv.append(emp.getEmployeesList().get(i).getLastName());
	csv.append("\n");
	}
	exchange.getIn().setBody(csv.toString());
    */
}