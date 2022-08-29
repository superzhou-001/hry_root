/**客户信息**/
DELETE from cu_bank;
DELETE from cu_car;
DELETE from cu_customer;
DELETE from cu_enterprise;
DELETE from cu_enterprise_budget;
DELETE from cu_enterprise_controperson;
DELETE from cu_enterprise_legalperson;
DELETE from cu_enterprise_person;
DELETE from cu_enterprise_user;
DELETE from cu_house;cu_intention;
DELETE from cu_intention_follow;
DELETE from cu_intention_follow_comment;
DELETE from cu_intention_info;
DELETE from cu_intention_person;
DELETE from cu_intention_user;
DELETE from cu_person;
DELETE from cu_update_log;

/**合同**/
DELETE from ct_contract_element;
DELETE from ct_contract_seal;
DELETE from ct_contract_template;
DELETE from ct_contract_template_element;
DELETE from ct_contract_template_seal;
DELETE from ct_contract_type;
DELETE from ct_table;
DELETE from ct_table_column;

/**授信**/
DELETE from cf_facility_contract;
DELETE from cf_facility_flow;
DELETE from cf_facility_guarantee;
DELETE from cf_facility_mortgage;
DELETE from cf_process_contract;

/**保理**/
DELETE from fa_bill; fa_cost;
DELETE from fa_cost_nlms;
DELETE from fa_factoring_bill;
DELETE from fa_factoring_contract;
DELETE from fa_factoring_cost;
DELETE from fa_factoring_flow;
DELETE from fa_factoring_rate;
DELETE from fa_fund_intent;
DELETE from fa_product;
DELETE from fa_product_contract;
DELETE from fa_product_cost;
DELETE from fa_product_rate;
DELETE from fa_product_type;