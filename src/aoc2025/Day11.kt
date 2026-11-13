package aoc2025

import AocApplication
import utils.Node
import kotlin.collections.forEach

class Day11: AocApplication {
    lateinit var nodeMap: MutableMap<String, Node<String>>
    lateinit var sumMap: MutableMap<String, Long>
    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        sumMap = mutableMapOf()
        val initNode = nodeMap["you"]
        return traceNode(initNode!!, "out")
    }

    override fun defaultPart2(input: List<String>): Any {
        sumMap = mutableMapOf()
        val fftScore =  traceNode(nodeMap["svr"] ?: Node(""), "fft")
        sumMap = mutableMapOf()
        val dacScore =  traceNode(nodeMap["fft"] ?: Node(""), "dac")
        sumMap = mutableMapOf()
        val outScore =  traceNode(nodeMap["dac"] ?: Node(""), "out")
        return fftScore * dacScore * outScore
    }

    override fun preprocess(input: List<String>) {
        nodeMap = mutableMapOf()
        input.forEach { it ->
            val front = it.split(": ")[0]
            val end  = it.split(": ")[1].split(' ')
            val frontNode = nodeMap.getOrDefault(front, Node(front, mutableListOf()))
            end.forEach { i ->
                if (!frontNode.children.contains(i)) frontNode.children.add(i)
                val endNode = nodeMap.getOrDefault(i, Node(i, mutableListOf()))
                nodeMap[i] = endNode
            }
            nodeMap[front] = frontNode
        }
    }

    private fun traceNode(node: Node<String>, target: String): Long {
        if (node.value == target) return 1L
        if (sumMap.getOrDefault(node.value, -1L) != -1L) return sumMap[node.value]!!
        if (node.children.isEmpty()) return 0L
        val sum =  node.children.map {
            traceNode(nodeMap[it]!!, target)
        }.sum()
        sumMap[node.value] = sum
        return sum
    }
}