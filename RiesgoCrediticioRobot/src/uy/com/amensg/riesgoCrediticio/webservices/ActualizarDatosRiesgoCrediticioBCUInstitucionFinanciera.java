package uy.com.amensg.riesgoCrediticio.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6",
    "arg7"
})
public class ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera {

    protected String arg0;
    protected String arg1;
    protected String arg2;
    protected String arg3;
    protected String arg4;
    protected String arg5;
    protected String arg6;
    protected String arg7;

    public String getArg0() {
        return arg0;
    }

    public void setArg0(String value) {
        this.arg0 = value;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String value) {
        this.arg1 = value;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String value) {
        this.arg2 = value;
    }

	public String getArg3() {
		return arg3;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	public String getArg4() {
		return arg4;
	}

	public void setArg4(String arg4) {
		this.arg4 = arg4;
	}

	public String getArg5() {
		return arg5;
	}

	public void setArg5(String arg5) {
		this.arg5 = arg5;
	}

	public String getArg6() {
		return arg6;
	}

	public void setArg6(String arg6) {
		this.arg6 = arg6;
	}

	public String getArg7() {
		return arg7;
	}

	public void setArg7(String arg7) {
		this.arg7 = arg7;
	}
}