package tecnico.ai.smarthome.domobus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DomobBusSystem {
	private String id;
	private String Name;
	private String div_id;
	private String div_name;
	private List<Scalar> scalars;
	private List<Enumerated> enums;
	private List<DeviceType> device_types;
	
	public DomobBusSystem(String string,String n,String string2,String dn){
		setId(string);
		setName(n);
		setDiv_id(string2);
		setDiv_name(dn);
		setScalars(new ArrayList<Scalar>());
		setEnums(new ArrayList<Enumerated>());
		setDevice_types(new ArrayList<DeviceType>());
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getDiv_id() {
		return div_id;
	}
	public void setDiv_id(String string2) {
		this.div_id = string2;
	}
	public String getDiv_name() {
		return div_name;
	}
	public void setDiv_name(String dn) {
		this.div_name = dn;
	}
	public List<Scalar> getScalars() {
		return scalars;
	}
	public void setScalars(List<Scalar> scalars) {
		this.scalars = scalars;
	}
	public List<Enumerated> getEnums() {
		return enums;
	}
	public void setEnums(List<Enumerated> enums) {
		this.enums = enums;
	}
	public List<DeviceType> getDevice_types() {
		return device_types;
	}
	public void setDevice_types(List<DeviceType> device_types) {
		this.device_types = device_types;
	}
	
}
