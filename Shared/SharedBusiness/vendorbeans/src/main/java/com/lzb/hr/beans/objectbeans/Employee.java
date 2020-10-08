package com.lzb.hr.beans.objectbeans;

import java.util.Map;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public  class Employee {

	@DataField(pos = 1 )
		private int empNo;
	@DataField(pos = 2)
		private String firstName;
	@DataField(pos = 3)
		private String lastName;
		
		public Employee(int empNo, String firstName, String lastName) {
			super();
			this.empNo = empNo;
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		public Employee(){
			
		}
		
		
		public int getId() {
			return empNo;
		}
		public void setId(int empNo) {
			this.empNo = empNo;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		@Override
		public String toString() {
			return "Employee [empNo=" + empNo + ", firstName=" + firstName + ", lastName=" + lastName + "]";
		}
		
		
		
		
}

