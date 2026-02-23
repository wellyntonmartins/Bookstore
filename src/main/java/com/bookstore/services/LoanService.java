package com.bookstore.services;

import com.bookstore.enums.LoanStatus;

import java.util.ArrayList;
import java.util.List;

public class LoanService {



    private static List<String> checkLoanStatusOrder(LoanStatus atual_status, String method_type) {
        List<String> available_status = new ArrayList<>();
        switch (method_type) {
            case "save":
                break;
            case "update":

            default:
                throw new InternalError("Failed to check Loan status order: method_type '" + method_type + "' isn't a valid method type.");
        }
    }
}
