package com.cjon.bank.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.dto.BankInquiryDTO;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankInquiryService;
import com.cjon.bank.service.BankSearchService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithdrawService;

@Controller
public class BankController {

	private DataSource dataSource;

	@Autowired
	public void setDatsSource(DataSource datsSource) {
		this.dataSource = datsSource;
	}

	private BankService service;

	// �츮�� view�� JSP�� �̿������ʾƿ�!!
	// ���� JSP�� �̿��ҰŸ� String�� return�Ǿ� �ϰ�
	// JSON�� ��������� ����Ϸ��� void�� ���
	// �츮�� Ŭ���̾�Ʈ�κ��� callback���� �޾ƾ� �ؿ�
	// ����� JSP�� �ƴ϶� Stream�� ��� Ŭ���̾�Ʈ���� JSON�� �����ؾ� �ؿ�

	@RequestMapping(value = "/selectAllMember")
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model) {

		// �Է�ó��
		String callback = request.getParameter("callback");

		// ����ó��
		service = new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.execute(model);

		// ���ó��
		// model���� ����� ������ �����!!
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		// ���� ��ü�� �����ؼ� ����ó���� �ؾ� �ؿ�!
		service = new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);

		// ���ó��
		boolean result = (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/search")
	public void search(HttpServletRequest request, HttpServletResponse response, Model model) {

		// �Է�ó��
		String callback = request.getParameter("callback");

		// ����ó��
		service = new BankSearchService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);

		// ���ó��
		// model���� ����� ������ �����!!
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/transfer")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model) {

		String callback = request.getParameter("callback");

		// ���� ��ü�� �����ؼ� ����ó���� �ؾ� �ؿ�!
		service = new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);

		// ���ó��
		boolean result = (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/inquiry")
	public void inquiry(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String callback = request.getParameter("callback");
		
		// ���� ��ü�� �����ؼ� ����ó���� �ؾ� �ؿ�!
		service = new BankInquiryService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		// ���ó��
		ArrayList<BankInquiryDTO> list = (ArrayList<BankInquiryDTO>) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
