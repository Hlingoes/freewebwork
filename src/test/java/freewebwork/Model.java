/**
 * @author SargerasWang
 */
package freewebwork;

import java.util.Date;

import com.cn.henry.freewebwork.utils.Excel.ExcelCell;

/**
 * The <code>Model</code>
 * 
 * @author SargerasWang Created at 2014年8月7日 下午5:09:29
 */
public class Model {
    @ExcelCell(index = 0)
    private String a;
    @ExcelCell(index = 1)
    private String b;
    @ExcelCell(index = 2)
    private String c;
    @ExcelCell(index = 3)
    private Date d;

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public Model(String a, String b, String c,Date d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * @return the a
     */
    public String getA() {
        return a;
    }

    /**
     * @param a
     *            the a to set
     */
    public void setA(String a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public String getB() {
        return b;
    }

    /**
     * @param b
     *            the b to set
     */
    public void setB(String b) {
        this.b = b;
    }

    /**
     * @return the c
     */
    public String getC() {
        return c;
    }

    /**
     * @param c
     *            the c to set
     */
    public void setC(String c) {
        this.c = c;
    }
}
