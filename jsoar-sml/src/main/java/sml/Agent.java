/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.35
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package sml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jsoar.kernel.RunType;
import org.jsoar.kernel.SoarException;
import org.jsoar.kernel.SoarProperties;
import org.jsoar.kernel.events.OutputEvent;
import org.jsoar.kernel.events.StopEvent;
import org.jsoar.kernel.memory.Wme;
import org.jsoar.kernel.rhs.functions.RhsFunctionHandler;
import org.jsoar.runtime.ThreadedAgent;
import org.jsoar.util.events.SoarEvent;
import org.jsoar.util.events.SoarEventListener;

import sml.connection.ErrorCode;

public class Agent extends ClientErrors
{
    final ThreadedAgent agent;
    
    protected final WorkingMemory m_WorkingMemory = new WorkingMemory(this);
    protected final Kernel m_Kernel;
    
    protected final ListMap<smlRunEventId, RunEventHandlerPlusData> m_RunEventMap = ListMap.newInstance();
    protected final ListMap<smlProductionEventId, ProductionEventHandlerPlusData>  m_ProductionEventMap = ListMap.newInstance();
    protected final ListMap<smlPrintEventId, PrintEventHandlerPlusData>            m_PrintEventMap = ListMap.newInstance();
    protected final ListMap<smlXMLEventId, XMLEventHandlerPlusData>                m_XMLEventMap = ListMap.newInstance();
    protected final ListMap<String, OutputEventHandlerPlusData>               m_OutputEventMap = ListMap.newInstance();
    protected final ListMap<smlWorkingMemoryEventId, OutputNotificationHandlerPlusData>    m_OutputNotificationMap = ListMap.newInstance();
    
    private final Map<Integer, RhsFunctionHandler> rhsFunctions = new HashMap<Integer, RhsFunctionHandler>();
    
    // Used to generate unique IDs for callbacks
    protected int m_CallbackIDCounter ;

    // Used to generate unique IDs for visited values when walking graph
    protected int m_VisitedCounter ;

    // Internally we register a print callback and store its id here.
    protected int m_XMLCallback ;

    // When true, if a wme is updated to the same value as before we "blink" the wme by removing
    // the old wme and adding a new one, causing rules to rematch in Soar.
    protected boolean m_BlinkIfNoChange ;
    
    private final Listener listener = new Listener();
    private smlRunResult lastResult = smlRunResult.sml_RUN_COMPLETED;
    private final Object agentStopMonitor = new String("Agent stop monitor");
    
    private final List<WMElement> pendingCommands = new ArrayList<WMElement>();
    
    public synchronized void delete()
    {
        this.agent.getEvents().removeListener(null, listener);
        this.agent.dispose();
        super.delete();
    }

    public Agent(ThreadedAgent agent)
    {
        this.agent = agent;
        this.agent.getEvents().addListener(StopEvent.class, listener);
        this.agent.getEvents().addListener(OutputEvent.class, listener);
        this.m_Kernel = null;
    }
    
    /**
     * @param kernel
     * @param agentName
     */
    Agent(Kernel kernel, String agentName)
    {
        this.agent = ThreadedAgent.create();
        this.agent.getEvents().addListener(StopEvent.class, listener);
        this.agent.getEvents().addListener(OutputEvent.class, listener);
        this.agent.setName(agentName);

        m_Kernel = kernel ;
        m_CallbackIDCounter = 0 ;
        m_VisitedCounter = 0 ;
        m_XMLCallback = -1 ;
        m_BlinkIfNoChange = true ;

        ClearError() ;
    }

    public interface RunEventInterface
    {
        public void runEventHandler(int eventID, Object data, Agent agent, int phase);
    }

    public interface ProductionEventInterface
    {
        public void productionEventHandler(int eventID, Object data, Agent agent, String prodName, String instantiation);
    }

    public interface PrintEventInterface
    {
        public void printEventHandler(int eventID, Object data, Agent agent, String message);
    }

    public interface xmlEventInterface
    {
        public void xmlEventHandler(int eventID, Object data, Agent agent, ClientXML xml);
    }

    public interface OutputEventInterface
    {
        public void outputEventHandler(Object data, String agentName, String attributeName, WMElement pWmeAdded);
    }

    public interface OutputNotificationInterface
    {
        public void outputNotificationHandler(Object data, Agent agent);
    }

    public int RegisterForRunEvent(smlRunEventId id, RunEventInterface handlerObject, Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int RegisterForProductionEvent(smlProductionEventId id, ProductionEventInterface handlerObject,
            Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int RegisterForPrintEvent(smlPrintEventId id, PrintEventInterface handlerObject, Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int RegisterForPrintEvent(smlPrintEventId id, PrintEventInterface handlerObject, Object callbackData,
            boolean ignoreOwnEchos)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int RegisterForXMLEvent(smlXMLEventId id, xmlEventInterface handlerObject, Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int RegisterForOutputNotification(OutputNotificationInterface handlerObject, Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean UnregisterForOutputNotification(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean UnregisterForRunEvent(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean UnregisterForProductionEvent(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean UnregisterForPrintEvent(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean UnregisterForXMLEvent(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int AddOutputHandler(String attributeName, OutputEventInterface handlerObject, Object callbackData)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean RemoveOutputHandler(int callbackReturnValue)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String GetAgentName()
    {
        return agent.getName();
    }

    public Kernel GetKernel()
    {
        return m_Kernel;
    }

    public boolean LoadProductions(String pFilename, boolean echoResults)
    {
        // gSKI's LoadProductions command doesn't support all of the command line commands we need,
        // so we'll send this through the command line processor instead by creating a "source" command.
        StringBuilder cmd = new StringBuilder();
        cmd.append("source ");
        if (pFilename.length() > 0 && (pFilename.charAt(0) == '\"') && (pFilename.charAt(pFilename.length() - 1) == '\"'))
        {
            cmd.append(pFilename);
        } else {
            cmd.append('\"');
            cmd.append(pFilename);
            cmd.append('\"');
        }

        // Execute the source command
        final String pResult = ExecuteCommandLine(cmd.toString(), echoResults) ;

        final boolean ok = GetLastCommandLineResult() ;

        if (ok)
            ClearError() ;
        else
            SetDetailedError(ErrorCode.kDetailedError, pResult) ;

        return ok ;
    }

    public boolean LoadProductions(String pFilename)
    {
        return LoadProductions(pFilename, true);
    }

    public Identifier GetInputLink()
    {
        return GetILink();
    }

    public Identifier GetILink()
    {
        return m_WorkingMemory.GetInputLink();
    }

    public Identifier GetOutputLink()
    {
        return m_WorkingMemory.GetOutputLink();
    }

    public StringElement CreateStringWME(Identifier parent, String pAttribute, String pValue)
    {
        return GetWM().CreateStringWME(parent, pAttribute, pValue);
    }

    public IntElement CreateIntWME(Identifier parent, String pAttribute, int value)
    {
        return GetWM().CreateIntWME(parent, pAttribute, value);
    }

    public FloatElement CreateFloatWME(Identifier parent, String pAttribute, double value)
    {
        return GetWM().CreateFloatWME(parent, pAttribute, value);
    }

    public Identifier CreateIdWME(Identifier parent, String pAttribute)
    {
        return GetWM().CreateIdWME(parent, pAttribute);
    }

    public Identifier CreateSharedIdWME(Identifier parent, String pAttribute, Identifier pSharedValue)
    {
        return GetWM().CreateSharedIdWME(parent, pAttribute, pSharedValue);
    }

    public void Update(StringElement pWME, String pValue)
    {
        GetWM().UpdateString(pWME, pValue);
    }

    public void Update(IntElement pWME, int value)
    {
        GetWM().UpdateInt(pWME, value);
    }

    public void Update(FloatElement pWME, double value)
    {
        GetWM().UpdateFloat(pWME, value);
    }

    public void SetBlinkIfNoChange(boolean state)
    {
        m_BlinkIfNoChange = state;
    }

    public boolean IsBlinkIfNoChange()
    {
        return m_BlinkIfNoChange;
    }

    public boolean DestroyWME(WMElement pWME)
    {
        return GetWM().DestroyWME(pWME);
    }

    public String InitSoar()
    {
        return ExecuteCommandLine("init-soar");
    }

    public int GetNumberOutputLinkChanges()
    {
        return 0; // TODO GetNumberOutputLinkChanges
    }

    public WMElement GetOutputLinkChange(int index)
    {
        return null; // TODO GetOutputLinkChange(int index)
    }

    public boolean IsOutputLinkChangeAdd(int index)
    {
        return false; // TODO IsOutputLinkChangeAdd(int index)
    }

    public void ClearOutputLinkChanges()
    {
        pendingCommands.clear();
    }

    public int GetNumberCommands()
    {
        return pendingCommands.size();
    }

    public boolean Commands()
    {
        return GetNumberCommands() > 0;
    }

    public Identifier GetCommand(int index)
    {
        return index < pendingCommands.size() ? pendingCommands.get(index).ConvertToIdentifier() : null;
    }

    private void updatePendingCommands()
    {
        pendingCommands.clear();
        for(Wme wme : agent.getInputOutput().getPendingCommands())
        {
            pendingCommands.add(GetWM().findWme(wme));
        }
    }
    
    public boolean Commit()
    {
        return true; // TODO
    }

    public boolean IsCommitRequired()
    {
        return false; // TODO
    }

    static RunType smlToJSoarRunType(smlRunStepSize stepSize)
    {
        switch(stepSize)
        {
        case sml_DECISION: return RunType.DECISIONS;
        case sml_ELABORATION: return RunType.ELABORATIONS;
        case sml_PHASE: return RunType.PHASES;
        case sml_UNTIL_OUTPUT: return RunType.MODIFICATIONS_OF_OUTPUT;
        default:
            throw new IllegalArgumentException("stepSize: " + stepSize);
        }
        
    }
    public String RunSelf(long numberSteps, smlRunStepSize stepSize)
    {
        final RunType runType = smlToJSoarRunType(stepSize);
        
        synchronized(agentStopMonitor)
        {
            agent.runFor(numberSteps, runType);
            waitForAgentToStop();
        }
        return "";
    }

    private void waitForAgentToStop()
    {
        while(agent.isRunning())
        {
            try
            {
                agentStopMonitor.wait();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public String RunSelf(long numberSteps)
    {
        return RunSelf(numberSteps, smlRunStepSize.sml_DECISION);
    }

    public String RunSelfForever()
    {
        synchronized(agentStopMonitor)
        {
            agent.runForever();
            waitForAgentToStop();
        }
        return "";
    }

    public String RunSelfTilOutput()
    {
        return RunSelf(0, smlRunStepSize.sml_UNTIL_OUTPUT);
    }

    public boolean WasAgentOnRunList()
    {
        return true;
    }

    public smlRunResult GetResultOfLastRun()
    {
        return lastResult;
    }

    public String StopSelf()
    {
        agent.stop();
        return "";
    }

    public void Refresh()
    {
        // throw new UnsupportedOperationException("Not implemented");
    }

    public smlPhase GetCurrentPhase()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int GetDecisionCycleCounter()
    {
        return agent.getProperties().get(SoarProperties.D_CYCLE_COUNT).intValue();
    }

    public smlRunState GetRunState()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String ExecuteCommandLine(final String pCommandLine, boolean echoResults, boolean noFilter)
    {
        ClearError();
        final Callable<String> call = new Callable<String>() {

            @Override
            public String call() throws Exception
            {
                return agent.getInterpreter().eval(pCommandLine);
            }};
        try
        {
            return execAndWait(call);
        }
        catch (SoarException e)
        {
            SetDetailedError(ErrorCode.kDetailedError, e.getMessage());
            return e.getMessage();
        }
    }

    public String ExecuteCommandLine(String pCommandLine, boolean echoResults)
    {
        return ExecuteCommandLine(pCommandLine, echoResults, false);
    }

    public String ExecuteCommandLine(String pCommandLine)
    {
        return ExecuteCommandLine(pCommandLine, true, false);
    }

    public boolean ExecuteCommandLineXML(String pCommandLine, ClientAnalyzedXML pResponse)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean GetLastCommandLineResult()
    {
        return !HadError();
    }

    public boolean IsProductionLoaded(String pProductionName)
    {
        return agent.getProductions().getProduction(pProductionName) != null;
    }

    public boolean SynchronizeInputLink()
    {
        // TODO throw new UnsupportedOperationException("Not implemented");
        return true;
    }

    public boolean SynchronizeOutputLink()
    {
        // TODO throw new UnsupportedOperationException("Not implemented");
        return true;
    }
    
    private <T> T execAndWait(Callable<T> call) throws SoarException
    {
        try
        {
            return agent.executeAndWait(call, Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new SoarException(e.getMessage(), e);
        }
        catch (ExecutionException e)
        {
            throw new SoarException(e.getMessage(), e);
        }
        catch (TimeoutException e)
        {
            throw new SoarException(e.getMessage(), e);
        }
    }

    /**
     * @return
     */
    WorkingMemory GetWM()
    {
        return m_WorkingMemory;
    }

    void addRhsFunction(int id, RhsFunctionHandler handler)
    {
        rhsFunctions.put(id, handler);
        agent.getRhsFunctions().registerHandler(handler);
    }
    
    void removeRhsFunction(int id)
    {
        final RhsFunctionHandler handler = rhsFunctions.remove(id);
        if(handler != null)
        {
            agent.getRhsFunctions().unregisterHandler(handler.getName());
        }
    }
    /**
     * 
     */
    void FireOutputNotification()
    {
        final smlWorkingMemoryEventId id = smlWorkingMemoryEventId.smlEVENT_OUTPUT_PHASE_CALLBACK ;

        // Look up the handler(s) from the map
        final List<OutputNotificationHandlerPlusData> pHandlers = m_OutputNotificationMap.getList(id) ;

        if (pHandlers == null)
            return ;

        // Go through the list of event handlers calling each in turn
        for (OutputNotificationHandlerPlusData handlerWithData : pHandlers)
        {
            OutputNotificationInterface handler = handlerWithData.m_Handler ;
            Object pUserData = handlerWithData.m_UserData ;

            // Call the handler
            handler.outputNotificationHandler(pUserData, this) ;
        }
    }

    /**
     * @return
     */
    boolean IsRegisteredForOutputEvent()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @param wme
     */
    void ReceivedOutputEvent(WMElement wme)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @return
     */
    boolean IsAutoCommitEnabled()
    {
        return GetWM().IsAutoCommitEnabled();
    }

    private class Listener implements SoarEventListener
    {

        /* (non-Javadoc)
         * @see org.jsoar.util.events.SoarEventListener#onEvent(org.jsoar.util.events.SoarEvent)
         */
        @Override
        public void onEvent(SoarEvent event)
        {
            if(event instanceof StopEvent)
            {
                synchronized (agentStopMonitor)
                {
                    agentStopMonitor.notifyAll();
                }
            }
            else if(event instanceof OutputEvent)
            {
                updatePendingCommands();
            }
        }
        
    }
}
