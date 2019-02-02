/*
Copyright (c) 2012, Ulrik Pagh Schultz, University of Southern Denmark
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the University of Southern Denmark.
*/

package statemachine.year2.microwaveoven;

import java.util.Arrays;
import java.util.List;

import statemachine.year2.framework.AbstractRuntimeState;
import statemachine.year2.framework.MachineDescription;
import statemachine.year2.framework.State;
import statemachine.year2.framework.Transition;
import statemachine.year2.microwaveoven.MicrowaveMachine.MMS;

public class MicrowaveMachine extends MachineDescription<MMS> {

	public static class MMS extends AbstractRuntimeState<MMS> {	}
	
    // States
    private State<MMS> STATE_INACTIVE, STATE_COOKING, STATE_DOOR_OPEN;
    
    // State machine definition
    public MicrowaveMachine() {
        STATE_INACTIVE = new State<MMS>("INACTIVE");
        STATE_INACTIVE.addTransition("START", new Transition<MMS>("COOKING"));
        STATE_COOKING = new State<MMS>("COOKING");
        STATE_COOKING.addTransition("TIMER", new Transition<MMS>("INACTIVE"));
        STATE_COOKING.addTransition("STOP", new Transition<MMS>("INACTIVE"));
        STATE_COOKING.addTransition("OPEN", new Transition<MMS>("DOOR_OPEN"));
        STATE_DOOR_OPEN = new State<MMS>("DOOR_OPEN");
        STATE_DOOR_OPEN.addTransition("CLOSE", new Transition<MMS>("COOKING"));
        STATE_DOOR_OPEN.addTransition("STOP", new Transition<MMS>("INACTIVE"));
    }
    
    @Override
    protected List<State<MMS>> getAllStates() {
        return Arrays.asList(STATE_INACTIVE, STATE_COOKING, STATE_DOOR_OPEN);
    }

	@Override
	protected MMS createRuntimeState() {
		return new MMS();
	}

}
