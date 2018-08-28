package com.eleven.jpromiser;

public class DebugWatcher {
    private static int originStackIndex = 2;

    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();
    }

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
    }

    public static String fullStack(String TAG, String message){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(".\r\n"+
                "┌─ " + TAG + " ────────────────────────────────────┐");

        sb.append("\r\n"+
                "│ " +
                message);
        for (StackTraceElement element :
                stackTrace) {
            sb.append("\r\n"+
                    "│ " +
                    element.toString());
        }
        sb.append("\r\n"+
                "└────────────────────────────────────────┘"
        );
        return sb.toString();
    }
    public static String codeTrack(String TAG, String message, int index, boolean print){
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n"+
                "┌─ " + TAG + " ───────────────────────────┐");
        sb.append("\r\n"+
                "│ " +
                message);
        sb.append("\r\n"+
                "│ " +
                Thread.currentThread().getStackTrace()[index].toString());
        sb.append("\r\n"+
                "└────────────────────────────────────────┘"
        );
        if(print)
            System.out.print(sb.toString());
        return sb.toString();
    }
    public static String codeTrack(String TAG, String message, String callMethod, int offsetIndex, boolean print){

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int callMethodStackIndex = 0;
        for(int i = 0;i < stackTrace.length; i++){

            if(stackTrace[i].toString().contains(callMethod)){
                callMethodStackIndex = i;
                break;
            }
        }
        if(callMethodStackIndex+offsetIndex >= stackTrace.length){
            return "offset index out!!!";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n . \n"+
                "┌─ " + TAG + " ───────────────────────────┐");
        sb.append("\n"+
                "│ " +
                message);
        sb.append("\n"+
                "│ " +
                stackTrace[callMethodStackIndex+offsetIndex].toString());
        sb.append("\n"+
                "└────────────────────────────────────────┘"
        );
        return sb.toString();
    }

}
