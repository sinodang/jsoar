/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.35
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package sml;

public enum smlStringEventId {
  smlEVENT_EDIT_PRODUCTION(smlUpdateEventId.smlEVENT_LAST_UPDATE_EVENT.swigValue() + 1),
  smlEVENT_LOAD_LIBRARY,
  smlEVENT_LAST_STRING_EVENT(smlEVENT_LOAD_LIBRARY);

  public final int swigValue() {
    return swigValue;
  }

  public static smlStringEventId swigToEnum(int swigValue) {
    smlStringEventId[] swigValues = smlStringEventId.class.getEnumConstants();
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (smlStringEventId swigEnum : swigValues)
      if (swigEnum.swigValue == swigValue)
        return swigEnum;
    throw new IllegalArgumentException("No enum " + smlStringEventId.class + " with value " + swigValue);
  }

  private smlStringEventId() {
    this.swigValue = SwigNext.next++;
  }

  private smlStringEventId(int swigValue) {
    this.swigValue = swigValue;
    SwigNext.next = swigValue+1;
  }

  private smlStringEventId(smlStringEventId swigEnum) {
    this.swigValue = swigEnum.swigValue;
    SwigNext.next = this.swigValue+1;
  }

  private final int swigValue;

  private static class SwigNext {
    private static int next = 0;
  }
}

