/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.35
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package sml;

public class OutputEventHandlerPlusData extends EventHandlerPlusData {
    /*
  private long swigCPtr;

  protected OutputEventHandlerPlusData(long cPtr, boolean cMemoryOwn) {
    super(smlJNI.SWIGOutputEventHandlerPlusDataUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OutputEventHandlerPlusData obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }
  */

  public synchronized void delete() {
      /*
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      smlJNI.delete_OutputEventHandlerPlusData(swigCPtr);
    }
    swigCPtr = 0;
    */
    super.delete();
  }

  public void setM_Handler(SWIGTYPE_p_f_p_void_p_sml__Agent_p_q_const__char_p_sml__WMElement__void value) {
      throw new UnsupportedOperationException("Not implemented");
  }

  public SWIGTYPE_p_f_p_void_p_sml__Agent_p_q_const__char_p_sml__WMElement__void getM_Handler() {
      throw new UnsupportedOperationException("Not implemented");
  }

  public void setM_AttributeName(String value) {
      throw new UnsupportedOperationException("Not implemented");
  }

  public String getM_AttributeName() {
      throw new UnsupportedOperationException("Not implemented");
  }

  public OutputEventHandlerPlusData() {
      throw new UnsupportedOperationException("Not implemented");
  }

  public OutputEventHandlerPlusData(int eventID, String pAttributeName, SWIGTYPE_p_f_p_void_p_sml__Agent_p_q_const__char_p_sml__WMElement__void handler, SWIGTYPE_p_void userData, int callbackID) {
      throw new UnsupportedOperationException("Not implemented");
  }

}