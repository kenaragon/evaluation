/******************************************************************************
 * AdministrateProducer.java - created by aaronz@vt.edu
 * 
 * Copyright (c) 2007 Virginia Polytechnic Institute and State University
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 * 
 * Contributors:
 * Aaron Zeckoski (aaronz@vt.edu)
 *****************************************************************************/

package org.sakaiproject.evaluation.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.evaluation.logic.EvalExternalLogic;
import org.sakaiproject.evaluation.logic.EvalSettings;
import org.sakaiproject.evaluation.tool.EvaluationConstant;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.rsf.components.ELReference;
import uk.org.ponder.rsf.components.UIBoundBoolean;
import uk.org.ponder.rsf.components.UIBoundList;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;

/**
 * Handles administration of the evaluation system
 * 
 * @author Aaron Zeckoski (aaronz@vt.edu)
 */
public class AdministrateProducer implements ViewComponentProducer, NavigationCaseReporter {

	public static final String VIEW_ID = "administrate"; //$NON-NLS-1$
	public String getViewID() {
		return VIEW_ID;
	}

	private EvalExternalLogic external;
	public void setExternal(EvalExternalLogic external) {
		this.external = external;
	}

	private EvalSettings settings;
	public void setSettings(EvalSettings settings) {
		this.settings = settings;
	}

	private MessageLocator messageLocator;
	public void setMessageLocator(MessageLocator messageLocator) {
		this.messageLocator = messageLocator;
	}	
	
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
		String currentUserId = external.getCurrentUserId();
		boolean userAdmin = external.isUserAdmin(currentUserId);

		if (! userAdmin) {
			// Security check and denial
			throw new SecurityException("Non-admin users may not access this page");
		}

		/*
		 * top links here
		 */
		UIOutput.make(tofill, "administrate-title", messageLocator.getMessage("administrate.page.title") ); //$NON-NLS-1$ //$NON-NLS-2$
		if (userAdmin) {
			UIInternalLink.make(tofill, "control-panel-toplink", messageLocator.getMessage("controlpanel.page.title"), //$NON-NLS-1$ //$NON-NLS-2$
					new SimpleViewParameters(ControlPanelProducer.VIEW_ID));
		}
		UIInternalLink.make(tofill, "summary-toplink", messageLocator.getMessage("summary.page.title"), new SimpleViewParameters(SummaryProducer.VIEW_ID)); //$NON-NLS-1$ //$NON-NLS-2$

		//System Settings
		UIForm form = UIForm.make(tofill, "basic-form"); //$NON-NLS-1$		
		UIOutput.make(form, "system-settings-header", messageLocator.getMessage("administrate.system.settings.header")); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "system-settings-instructions", messageLocator.getMessage("administrate.system.settings.instructions")); //$NON-NLS-1$ //$NON-NLS-2$

		//Instructor Settings
		UIOutput.make(form, "instructor-settings-header", messageLocator.getMessage("administrate.instructor.settings.header")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "instructors-eval-create", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "instructors-eval-create-note", messageLocator.getMessage("administrate.instructors.eval.create.note")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "instructors-view-results", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "instructors-view-results-note", messageLocator.getMessage("administrate.instructors.view.results.note")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "instructors-email-students", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "instructors-email-students-note", messageLocator.getMessage("administrate.instructors.email.students.note")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "instructors-hierarchy-email", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "instructors-hierarchy-email-note", messageLocator.getMessage("administrate.instructors.hierarchy.email.note")); //$NON-NLS-1$ //$NON-NLS-2$
		
		//Select for number of questions intructors can add
		UISelect numQuestionsInst = UISelect.make(form, "instructors-num-questions"); //$NON-NLS-1$
		numQuestionsInst.selection = new UIInput();
		numQuestionsInst.selection.valuebinding = new ELReference("#{????.????}"); //$NON-NLS-1$
		UIBoundList numQuestionsInstValues = new UIBoundList();
		numQuestionsInstValues.setValue(EvaluationConstant.NUM_QUESTIONS_INST_ADMINS);
		numQuestionsInst.optionlist = numQuestionsInstValues;
		numQuestionsInst.optionnames = numQuestionsInstValues;	
		UIOutput.make(form, "instructors-num-questions-note", messageLocator.getMessage("administrate.instructors.num.questions.note")); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Student Settings
		UIOutput.make(form, "student-settings-header", messageLocator.getMessage("administrate.student.settings.header")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "students-unanswered", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "students-unanswered-note", messageLocator.getMessage("administrate.students.unanswered.note")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "students-modify-responses", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "students-modify-responses-note", messageLocator.getMessage("administrate.students.modify.responses.note")); //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "students-view-results", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "students-view-results-note", messageLocator.getMessage("administrate.students.view.results.note")); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Administrator Settings
		UIOutput.make(form, "administrator-settings-header", messageLocator.getMessage("administrate.admin.settings.header"));		 //$NON-NLS-1$ //$NON-NLS-2$

		//Select for number of questions admins can add
		UISelect numQuestionsAdmin = UISelect.make(form, "admin-hierarchy-num-questions"); //$NON-NLS-1$
		numQuestionsAdmin.selection = new UIInput();
		numQuestionsAdmin.selection.valuebinding = new ELReference("#{????.????}"); //$NON-NLS-1$
		UIBoundList numQuestionsAdminValues = new UIBoundList();
		numQuestionsAdminValues.setValue(EvaluationConstant.NUM_QUESTIONS_INST_ADMINS);
		numQuestionsAdmin.optionlist = numQuestionsAdminValues;
		numQuestionsAdmin.optionnames = numQuestionsAdminValues;	
		UIOutput.make(form, "admin-hierarchy-num-questions-note", messageLocator.getMessage("administrate.admin.hierarchy.num.questions.note")); //$NON-NLS-1$ //$NON-NLS-2$

		UIBoundBoolean.make(form, "admin-view-instructor-questions", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "admin-view-instructor-questions-note", messageLocator.getMessage("administrate.admin.view.instructor.questions.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "admin-super-modify-question", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "admin-super-modify-question-note", messageLocator.getMessage("administrate.admin.super.modify.question.note")); //$NON-NLS-1$ //$NON-NLS-2$
		
		UIOutput.make(form, "general-settings-header", messageLocator.getMessage("administrate.general.settings.header"));		 //$NON-NLS-1$ //$NON-NLS-2$
		UIInput.make(form, "general-helpdesk-email", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-helpdesk-email-note", messageLocator.getMessage("administrate.general.helpdesk.email.note")); //$NON-NLS-1$ //$NON-NLS-2$
		
		// Select for number of responses before results could be viewed
		UISelect numResponsesBeforeView= UISelect.make(form, "general-responses-before-view"); //$NON-NLS-1$
		numResponsesBeforeView.selection = new UIInput();
		numResponsesBeforeView.selection.valuebinding = new ELReference("#{????.????}"); //$NON-NLS-1$
		UIBoundList numResponsesBeforeViewValues = new UIBoundList();
		numResponsesBeforeViewValues.setValue(EvaluationConstant.NUM_RESPONSES_BEFORE_VIEW_RESULTS);
		numResponsesBeforeView.optionlist = numResponsesBeforeViewValues;
		numResponsesBeforeView.optionnames = numResponsesBeforeViewValues;	
		UIOutput.make(form, "general-responses-before-view-note", messageLocator.getMessage("administrate.general.responses.before.view.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		
		UIBoundBoolean.make(form, "general-na-allowed", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-na-allowed-note", messageLocator.getMessage("administrate.general.na.allowed.note"));	 //$NON-NLS-1$ //$NON-NLS-2$
		
		// Select for maximum number of questions in a block
		UISelect maxQuestionsInBlock = UISelect.make(form, "general-max-questions-block"); //$NON-NLS-1$
		maxQuestionsInBlock.selection = new UIInput();
		maxQuestionsInBlock.selection.valuebinding = new ELReference("#{????.????}"); //$NON-NLS-1$
		UIBoundList maxQuestionsInBlockValues = new UIBoundList();
		maxQuestionsInBlockValues.setValue(EvaluationConstant.MAX_QUESTIONS_IN_BLOCK);
		maxQuestionsInBlock.optionlist = maxQuestionsInBlockValues;
		maxQuestionsInBlock.optionnames = maxQuestionsInBlockValues;	
		UIOutput.make(form, "general-max-questions-block-note", messageLocator.getMessage("administrate.general.max.questions.block.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		
		// Select for template sharing and visibility settings
		UISelect templateSharingVisibility = UISelect.make(form, "general-template-sharing"); //$NON-NLS-1$
		templateSharingVisibility.selection = new UIInput();
		templateSharingVisibility.selection.valuebinding = new ELReference("#{????.????}"); //$NON-NLS-1$
		UIBoundList templateSharingVisibilityValues = new UIBoundList();
		String[] sharingList = 
		{
			messageLocator.getMessage("modifytemplatetitledesc.sharing.private"),
			messageLocator.getMessage("modifytemplatetitledesc.sharing.visible"),
			messageLocator.getMessage("modifytemplatetitledesc.sharing.shared"),
			messageLocator.getMessage("modifytemplatetitledesc.sharing.public")
		};		
		templateSharingVisibilityValues.setValue(sharingList);
		templateSharingVisibility.optionlist = templateSharingVisibilityValues;
		templateSharingVisibility.optionnames = templateSharingVisibilityValues;	
		UIOutput.make(form, "general-template-sharing-note", messageLocator.getMessage("administrate.general.template.sharing.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		
		UIBoundBoolean.make(form, "general-default-question", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-default-question-category", messageLocator.getMessage("administrate.general.default.question.category.note"));	 //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "general-use-stop-date", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-use-stop-date-note", messageLocator.getMessage("administrate.general.use.stop.date.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "general-expert-templates", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-expert-templates-note", messageLocator.getMessage("administrate.general.expert.templates.note"));	 //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "general-expert-questions", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-expert-questions-note", messageLocator.getMessage("administrate.general.expert.questions.note"));		 //$NON-NLS-1$ //$NON-NLS-2$
		UIBoundBoolean.make(form, "general-same-view-date", "#{????.????}", null); //$NON-NLS-1$ //$NON-NLS-2$
		UIOutput.make(form, "general-same-view-date-note", messageLocator.getMessage("administrate.general.same.view.date.note"));	 //$NON-NLS-1$ //$NON-NLS-2$

		// Save settings button
		UICommand.make(form, "saveSettings", messageLocator.getMessage("administrate.save.settings.button"), "#{templateBean.createTemplateAction}"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$		
	}
	
	public List reportNavigationCases() {
		List i = new ArrayList();
		//TODO
		return i;
	}
}
