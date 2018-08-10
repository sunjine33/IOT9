package com.sunjine33.iot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sunjine33.iot.domain.Product;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@RequestMapping("doA")
	public void doA() {

		logger.info("doA called.............");

	}

	@RequestMapping("doB")
	public String doB() {

		logger.info("doB called.............");
		return "sample_b";
	}
	
	@RequestMapping("doC")
	public String doC(
			@ModelAttribute("msg") String msg,
			@ModelAttribute("id") String id,
			Model model
			) {
		
		logger.info("doC called.............");
		
		model.addAttribute("title", "product");
		Product p = new Product("담배",4500);
		model.addAttribute(p);
		
		return "sample_c";
	}

	@RequestMapping("doR")
	public String doR(RedirectAttributes rdta, @ModelAttribute("id") String id) {
		
		rdta.addFlashAttribute("msg", "This page not found");
		rdta.addAttribute("id", id);
		
		return "redirect:/doC";
	}
	

	@RequestMapping("Json")
	public @ResponseBody Product Json() {
		Product prd = new Product("쏘나타", 200000000);
		return prd;
	}
}
