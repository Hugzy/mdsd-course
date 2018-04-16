/*
 * generated by Xtext 2.12.0
 */
package dk.sdu.mmmi.mdsd.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import javax.swing.JOptionPane
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.MathExp
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Plus
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Minus
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Mult
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Div
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Expression
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Num
import java.util.HashMap
import java.util.Map
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Var
import dk.sdu.mmmi.mdsd.mathAssignmentLanguage.Let

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class MathAssignmentLanguageGenerator extends AbstractGenerator {

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val math = resource.allContents.filter(MathExp).next
		val result = math.compute
		System.out.println("Math expression = "+math.display)
		JOptionPane.showMessageDialog(null, "result = "+result,"Math Language", JOptionPane.INFORMATION_MESSAGE)
	}
	
	//
	// Compute function: computes value of expression
	// Note: written according to illegal left-recursive grammar, requires fix
	//
	
	def int compute(MathExp math) { 
		math.exp.computeExp(new HashMap<String,Integer>)
	}
	
	def int computeExp(Expression exp, Map<String,Integer> env) {
		switch exp {
			Plus: exp.left.computeExp(env)+exp.right.computeExp(env)
			Minus: exp.left.computeExp(env)-exp.right.computeExp(env)
			Mult: exp.left.computeExp(env)*exp.right.computeExp(env)
			Div: exp.left.computeExp(env)/exp.right.computeExp(env)
			Num: exp.value
			Var: env.get(exp.id)
			Let: exp.body.computeExp(env.bind(exp.id,exp.binding.computeExp(env)))
			default: throw new Error("Invalid expression")
		}
	}
	
	def Map<String, Integer> bind(Map<String, Integer> env1, String name, int value) {
		val env2 = new HashMap<String,Integer>(env1)
		env2.put(name,value)
		env2 
	}

	//
	// Display function: show complete syntax tree
	// Note: written according to illegal left-recursive grammar, requires fix
	//

	def String display(MathExp math) { 
		math.exp.displayExp
	}
	
	def String displayExp(Expression exp) {
		"("+switch exp {
			Plus: exp.left.displayExp+"+"+exp.right.displayExp
			Minus: exp.left.displayExp+"-"+exp.right.displayExp
			Mult: exp.left.displayExp+"*"+exp.right.displayExp
			Div: exp.left.displayExp+"/"+exp.right.displayExp
			Num: Integer.toString(exp.value)
			Var: exp.id
			Let: '''let «exp.id» = «exp.binding.displayExp» in «exp.body.displayExp» end'''
			default: throw new Error("Invalid expression")
		}+")"
	}
	
		
}
