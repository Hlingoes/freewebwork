/**
 * @author Hlingoes 2017-10-15 17:25:34
 * @cite http://www.w3dev.cn/article/20170713/fill-form-with-json.aspx
 */
function fillFormDataWithJson(f, kv) {
    var attr, el, v, vs;
    for (attr in kv) {
        el = f[attr];
        if (el) {
            v = kv[attr];
            switch (el.type||el[0].type) {
                case 'text':
                case 'textarea':
                case 'select-one':
                    el.value = v;
                    break;
                case 'select-multiple':
                    vs = ',' + v.join() + ',';// 前后加逗号分隔
                    for (var i = el.options.length - 1; i >= 0; i--) {
                    	if (vs.indexOf(',' + el.options[i].value + ',') != -1) {
                    		el.options[i].selected = true;
                    	}
                    }
                    break;
                case 'radio':
                case 'checkbox':
                    var isRadio = el.type == 'radio';
                    // 前后加逗号分隔
                    if (isRadio) vs = ',' + v.join() + ',';
                    else vs = ',' + v + ',';
                    if (typeof el.length == 'number') {// 多个
                        for (var i = el.length - 1; i >= 0; i--) {
                        	if (vs.indexOf(',' + el[i].value + ',') != -1) { 
                        		el[i].checked = true; if (isRadio) break; 
                        	}
                        }
                    }
                    else el.checked = el.value == v;// 只有一个
                    break;
            }
        }
    }
}