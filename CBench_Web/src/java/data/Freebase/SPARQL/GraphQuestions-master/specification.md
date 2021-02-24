### Data Format

Each file, graphquestions.[train/test].json, contains an array of JSON objects, each corresponding to a question (not graph query). The detailed format of a question is as follows. Note that if you only use question-answer pairs and do not rely on the logic forms, you can ignore "graph_query" and "sparql_query".

- qid: question id. A variable-digit number. The right-most two digits are for entity paraphrases, the middle four digits are for sentence paraphrases, and the rest comprise the id of the corresponding graph query this question is derived from. The questions with the same graph query id therefore are paraphrases.
- question: the natural language question. Lower-cased
- answer: the answer set, an array of strings. For an entities, the answer is its canonical name in Freebase derived from the type.object.name predicate; for a literal, the answer is its literal form (without data type specification). We provide answers in human readable form instead of Freebase specific form like mid to facilitate the use of the dataset on other data sources like DBpedia or the Web.
- function: the function of a question, ["count", "max", "min", "argmax", "argmin", ">", ">=", "<", "<=", "none"]. A question will have at most one function
- commonness: the log probability of the corresponding graph query
- num_node: number of nodes of the corresponding graph query
- num_edge: number of edges of the corresponding graph query
- graph_query: the corresponding graph query
    - nodes: the nodes, an array of JSON objects 
        - nid: node id, starting from 0
        - node_type: ["class", "entity", "literal"]. "class" node is either the question node or an ungrounded node, while "entity" and "literal" nodes are grounded
        - id: the Freebase unique ID of the node. For a class, it's the class name; for an entity, it's the mid; fora literal, it's the lexical form along with the data type
        - friendly_name: the canonical name of the node from Freebase, only for human readability
        - question_node: whether the node is the question node, [1, 0]. Actually it's useless for now because we currently only have one question node, which is always node 0
        - function: the function applied on the node
    - edges: the edges, an array of JSON objects
        - start: the node id of the starting node
        - end: the node id of the ending node
        - relation: the Freebase id of the relation on the edge
        - friendly_name: the Freebase canonical name of the relation, only for human readability
- sparql_query: the SPARQL query that is actually used to generate the answer. Note that the provided query will only get the Freebase id of the answer, and you need to convert it into the human readable format as described previously
