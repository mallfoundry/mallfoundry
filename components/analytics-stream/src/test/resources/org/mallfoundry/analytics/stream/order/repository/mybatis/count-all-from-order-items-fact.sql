select store_id_,
       customer_id_,
       product_id_,
       variant_id_,
       date_id_,
       sum(pending_quantity_)              as pending_quantity_,
       sum(awaiting_payment_quantity_)     as awaiting_payment_quantity_,
       sum(awaiting_fulfillment_quantity_) as awaiting_fulfillment_quantity_,
       sum(awaiting_shipment_quantity_)    as awaiting_shipment_quantity_,
       sum(partially_shipped_quantity_)    as partially_shipped_quantity_,
       sum(shipped_quantity_)              as shipped_quantity_,
       sum(awaiting_pickup_quantity_)      as awaiting_pickup_quantity_,
       sum(partially_refunded_quantity_)   as partially_refunded_quantity_,
       sum(refunded_quantity_)             as refunded_quantity_,
       sum(disputed_quantity_)             as disputed_quantity_,
       sum(cancelled_quantity_)            as cancelled_quantity_,
       sum(completed_quantity_)            as completed_quantity_,
       sum(declined_quantity_)             as declined_quantity_
from (
         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                count(id_)       as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'pending'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                count(id_)       as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'awaiting_payment'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                count(id_)       as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'awaiting_fulfillment'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                count(id_)       as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'awaiting_shipment'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                count(id_)       as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'partially_shipped'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                count(id_)       as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'shipped'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                count(id_)       as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'awaiting_pickup'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                count(id_)       as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'partially_refunded'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                count(id_)       as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'refunded'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                count(id_)       as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'disputed'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                count(id_)       as cancelled_quantity_,
                0                as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'cancelled'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                count(id_)       as completed_quantity_,
                0                as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'completed'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_

         union all

         select store_id_,
                customer_id_,
                product_id_,
                variant_id_,
                created_date_id_ as date_id_,
                0                as pending_quantity_,
                0                as awaiting_payment_quantity_,
                0                as awaiting_fulfillment_quantity_,
                0                as awaiting_shipment_quantity_,
                0                as partially_shipped_quantity_,
                0                as shipped_quantity_,
                0                as awaiting_pickup_quantity_,
                0                as partially_refunded_quantity_,
                0                as refunded_quantity_,
                0                as disputed_quantity_,
                0                as cancelled_quantity_,
                0                as completed_quantity_,
                count(id_)       as declined_quantity_
         from mf_dw_order_item_fact
         where store_id_ = 'mi'
           and customer_id_ = '1'
           and product_id_ = '229'
           and variant_id_ = '2123'
           and created_date_id_ = '20200707'
           and status_id_ = 'declined'
         group by store_id_, customer_id_, product_id_, variant_id_, created_date_id_
     ) as order_quantity
group by store_id_, customer_id_, product_id_, variant_id_, date_id_