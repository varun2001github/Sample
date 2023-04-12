//$Id$
package ApiEndpoint;

public class UrlMap{
	String packageName=null;
	String method=null;
	Object parameter=null;
	String methodType=null;

	public UrlMap(String packageNAme,String method,Object parameter,String methodType){
		this.packageName=packageNAme;
		this.method=method;
		this.parameter=parameter;
		this.methodType=methodType;
	}
	public UrlMap(String packageNAme,String method,String methodType){
		this.packageName=packageNAme;
		this.method=method;
		this.methodType=methodType;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Object getParameter() {
		return parameter;
	}
	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}
	public String getMethodType(){
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
}
