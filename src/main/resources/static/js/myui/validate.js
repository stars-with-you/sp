$.extend($.fn.validatebox.defaults.rules, {  
    /*必须和某个字段相等*/
    equalTo: {
        validator:function(value,param){
            return $(param[0]).val() == value;
        },
        message:'字段不匹配'
    },
    mobile: {
        validator: function (value, param) {
          return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
        },
        message: '手机号码不正确'
      },
      englishOrNum : {// 只能输入英文和数字
          validator : function(value) {
              return /^[a-zA-Z0-9_ ]{1,}$/.test(value);
          },
          message : '请输入英文、数字、下划线或者空格'
      },
      remote: {  
          validator: function(value, param){  
              var data = {};  
              data[param[1]] = value;  
              var response = $.ajax({  
                  url:param[0],  
                  dataType:'json',  
                  data:data,  
                  async:false,  
                  cache:false,  
                  type:'post'  
              }).responseText;  
              return response == 'true';  
          },  
          message: '企业帐号已经存在'  
      } ,
      length: {  
          validator: function(value, param){  
              var len = $.trim(value).length;  
              return len >= param[0] && len <= param[1]; 
          },  
          message: '请输入一个值，长度在 {0} - {1}之间'
      },
        idCode:{
          validator:function(value,param){
        	  var flag=checkIdcard(value);
              return flag==true?true:false;
          },
          message: '请输入正确的身份证号'
        }
      
});
//校验身份证合法性
function checkIdcard(idcard){ 
        var Errors=new Array( 
                            "验证通过!", 
                            "身份证号码位数不对!", 
                            "身份证号码出生日期超出范围或含有非法字符!", 
                            "身份证号码校验错误!", 
                            "身份证地区非法!" 
                            ); 
        var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 
                             
        //var idcard=idcard;
        var Y,JYM; 
        var S,M; 
        var idcard_array = new Array(); 
        idcard_array     = idcard.split(""); 
        //地区检验 
        if(area[parseInt(idcard.substr(0,2))]==null){
            //alert(Errors[4]); 
            //setItemFocus(0, 0, "CertID");
            return Errors[4];
        }
         
        //身份号码位数及格式检验 
        switch(idcard.length){
        case 15: 
            if((parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
                ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
            }else{ 
                ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
            } 
         
            if(ereg.test(idcard)){
                //alert(Errors[0]);
                //setItemFocus(0, 0, "CertID");
                return true;
                
            }else{ 
                //alert(Errors[2]);
                //setItemFocus(0, 0, "CertID");
                return Errors[2];
            }
            break; 
        case 18: 
            //18位身份号码检测 
            //出生日期的合法性检查  
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
            if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
                ereg=/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
            }else{
                ereg=/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
            } 
            if(ereg.test(idcard)){//测试出生日期的合法性 
                //计算校验位 
                S  =  (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
                    + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
                    + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
                    + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
                    + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
                    + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
                    + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
                    +  parseInt(idcard_array[7]) * 1  
                    +  parseInt(idcard_array[8]) * 6 
                    +  parseInt(idcard_array[9]) * 3 ; 
                Y    = S % 11; 
                M    = "F"; 
                JYM  = "10X98765432"; 
                M    = JYM.substr(Y,1);//判断校验位 
                if(M == idcard_array[17]){
                    return  true;
                    //Errors[0];        //检测ID的校验位 
                }else{
                    //alert(Errors[3]);
                    //setItemFocus(0, 0, "CertID");
                    return Errors[3];
                }
            }else{
                //alert(Errors[2]);
                //setItemFocus(0, 0, "CertID");
                return Errors[2];
            }
            break;
        default:
            //alert(Errors[1]);
            //setItemFocus(0, 0, "CertID");
            return Errors[1];
            break;
        }     
}