/**************************************************************************
*
* Copyright 2022 (C) Bitify s.r.l.
*
* Created on  : 2022-04-24
* Author      : A. Di Raffaele
* Project Name: esercizio 
* Package     : controller
* File Name   : AccountController.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.bitify.esercizio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.bitify.esercizio.service.AccountService;
import it.bitify.esercizio.model.Account;
import it.bitify.esercizio.dto.PagedResponse;
import it.bitify.esercizio.dto.ApiResponse;
import it.bitify.esercizio.util.AppConstants;


/*******************************************************************************************
 * Created by A. Di Raffaele 
 * The Account
 ******************************************************************************************/
@RestController
@RequestMapping("/api/account")
public class AccountController {
	
   Logger logger = LoggerFactory.getLogger(AccountController.class);
	
   @Autowired
   AccountService accountService;

   //@PreAuthorize("hasAuthority('account_list_all')")
   @GetMapping("/all")
   public ResponseEntity<Object> getAllAccounts() {
      return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
   }
   
   //@PreAuthorize("hasAuthority('account_list_paged')")
   @GetMapping
   public PagedResponse<Account> getAccounts( @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               				  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
														      @RequestParam(value = "sortdir", defaultValue = "1") int sortdir,
														      @RequestParam(value = "sortfield", defaultValue = "accountId") String sortfield,
   															  @RequestParam(value = "searchstring", defaultValue = "") String searchstring) {
   																  
       return accountService.getAllPaged(page, size, sortdir,sortfield,searchstring);
   }
   
   //@PreAuthorize("hasAuthority('account_by_id')")
   @GetMapping("/{id}")
   public Account getAccountById(@PathVariable Long id) {
       return accountService.findById(id).get();
   }
   
   //@PreAuthorize("hasAuthority('account_update')")
   @PutMapping
   public ResponseEntity<Object> 
      updateAccount(@RequestBody Account account) {
      
	   accountService.updateAccount(account);
      return ResponseEntity.ok(new ApiResponse(true, "Account is updated successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('account_delete')")
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
	   accountService.deleteAccount(id);
      return ResponseEntity.ok(new ApiResponse(true, "Account is deleted successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('account_delete_all')")
   @PutMapping("/deleteAll")
   public ResponseEntity<Object> 
      deleteAccounts(@RequestBody Account[] accounts) {
      
	   accountService.deleteAccounts(accounts);
      return ResponseEntity.ok(new ApiResponse(true, "Accounts are deleted successsfully"));
   }
   
   //@PreAuthorize("hasAuthority('account_create')")
   @PostMapping
   public ResponseEntity<Object> createAccount(@RequestBody Account account) {
	   accountService.createAccount(account);
      return ResponseEntity.ok(new ApiResponse(true, "Account is created successsfully"));
   }
   	
        //@PreAuthorize("hasAuthority('account_report_pdf')")
	@GetMapping("/report/pdf")
	public void generateReportPdf(HttpServletResponse response) {
		accountService.generateReportPdf(response);
	}
        //@PreAuthorize("hasAuthority('account_report_xls')")
	@GetMapping("/report/xls")
	public void generateReportXls(HttpServletResponse response) {
		accountService.generateReportXls(response);
	}
        //@PreAuthorize("hasAuthority('account_report_csv')")
	@GetMapping("/report/csv")
	public void generateReportCsv(HttpServletResponse response) {
		accountService.generateReportCsv(response);
	}
   
}