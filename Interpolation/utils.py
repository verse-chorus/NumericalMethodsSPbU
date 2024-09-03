import numpy as np
import matplotlib.pyplot as plt
import json

with open("cfg.json", "r") as f:
    cfg = json.load(f)

xarr = np.linspace(cfg['params']['a'],
                        cfg['params']['b'],
                        cfg['params']['m'])

# Исходная функция
def fn(x):
    return x**2 + 4*np.sin(x) - 2 

def get_deviations(function, a, b, k, max_nodes):
    # mds = maximum difference segment
    mds = np.linspace(a, b, k)

    # значения точной функции в точках x0, x1, ..., xm; m >> n
    func_mds = [fn(i) for i in mds]

    r1, r2 = [], []
    nodes = []
    for n_nodes in range(3, max_nodes + 1):
        f_mds, optimal_f_mds = [], []

        # initialize interpolation functions
        f = function(a, b, n_nodes)
        f_opt = function(a, b, n_nodes, 'optimal')

        # Ищем отклонения     
        for point in mds:
            # значения интерполяционной функции в точках x0, x1, ..., xk; k >> n  
            f_mds.append(f(point))

            # значения оптимальной интерполяционной функции в точках x0, x1, ..., xk; k >> n   
            if function.__name__ == 'Newton':
                point = max(f_opt.nodes[0], min(f_opt.nodes[-1], point))
            else:
                point = max(f_opt.x[0], min(f_opt.x[-1], point))
            optimal_f_mds.append(f_opt(point))    

        # Отклонения
        RSn_max = max(np.abs(np.array(func_mds) - np.array(f_mds)))
        RSn_opt_max = max([np.abs(func_mds[j] - optimal_f_mds[j]) for j in range(len(mds))])
        
        r1.append(RSn_max)
        r2.append(RSn_opt_max)
        nodes.append(n_nodes)

    return r1, r2, nodes